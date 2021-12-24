package org.lxy.swing.server.service;

import org.lxy.swing.server.model.NnUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Cassius
 * @since 2021-12-23
 */
public interface INnUserService extends IService<NnUser> {

    NnUser findUser(String username, String password);

}
