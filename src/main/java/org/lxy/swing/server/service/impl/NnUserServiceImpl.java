package org.lxy.swing.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lxy.swing.server.model.NnUser;
import org.lxy.swing.server.mapper.NnUserMapper;
import org.lxy.swing.server.service.INnUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Cassius
 * @since 2021-12-23
 */
@Service
public class NnUserServiceImpl extends ServiceImpl<NnUserMapper, NnUser> implements INnUserService {

    @Override
    public NnUser findUser(String username, String password) {
        return getOne(Wrappers.<NnUser>lambdaQuery()
                .eq(NnUser::getUserName,username)
                .eq(NnUser::getPassWord,password));
    }
}
