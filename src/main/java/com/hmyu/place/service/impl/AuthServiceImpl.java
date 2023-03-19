package com.hmyu.place.service.impl;

import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.mapper.UserMapper;
import com.hmyu.place.service.AuthService;
import com.hmyu.place.service.JwtService;
import com.hmyu.place.vo.ResponseVo;
import com.hmyu.place.vo.auth.ReqTokenVo;
import com.hmyu.place.vo.auth.ResTokenVo;
import com.hmyu.place.vo.auth.UserInfoVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseVo createToken(ReqTokenVo vo) throws Exception {
        ResponseVo resVo = new ResponseVo();

        if (vo == null) {
            resVo.setResultMessage(MessageConstant.INVALID_PARAMETER);
            return resVo;
        } else if (StringUtils.isEmpty(vo.getEmail())) {
            resVo.setResultMessage(MessageConstant.INVALID_PARAMETER, "email");
            return resVo;
        } else if (StringUtils.isEmpty(vo.getName())) {
            resVo.setResultMessage(MessageConstant.INVALID_PARAMETER, "name");
            return resVo;
        }

        boolean existUser = userMapper.selectUserCountByEmail(vo.getEmail()) > 0;
        if (!existUser) {
            int insertCnt = userMapper.insertUser(vo);
            if (insertCnt == 0) {
                resVo.setResultMessage(MessageConstant.INSERT_USER_ERROR);
            }
        }

        String userUuid = userMapper.selectUserUuidByEmail(vo.getEmail());
        logger.debug("[createToken] userUuid : {}", userUuid);

        UserInfoVo userInfo = new UserInfoVo();
        userInfo.setUserUuid(userUuid);
        userInfo.setEmail(vo.getEmail());
        userInfo.setName(vo.getName());

        String token = jwtService.create("userInfo", userInfo, userUuid);
        logger.debug("[createToken] token : {}", token);

        ResTokenVo resTokenVo = new ResTokenVo();
        resTokenVo.setUserInfo(userInfo);
        resTokenVo.setToken(token);

        resVo.setData(resTokenVo);
        return resVo;
    }
}
