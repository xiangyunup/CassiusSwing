package org.lxy.swing.panel.niuniu;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import org.lxy.swing.config.SpringContext;
import org.lxy.swing.constant.LanguageEnum;
import org.lxy.swing.constant.SwingConstant;
import org.lxy.swing.panel.HomeJPanel;
import org.lxy.swing.panel.LoginJPanel;
import org.lxy.swing.panel.niuniu.dto.Brands;
import org.lxy.swing.server.model.NnUser;
import org.lxy.swing.util.*;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.lxy.swing.constant.SwingConstant.*;

/**
 * @author Cassius
 * @date 2021/12/20 6:17 PM
 * @description 牛牛面板
 */
@Getter
@Component
public class NiuNiuJPanel {
    // 显示字牌宽高
    public static final int PAI_WIDTH = 60;
    public static final int PAI_HEIGHT = 50;
    // 下方按钮下沉比例
    public static final double SINK_SCALE_W = 0.35;
    public static final double SINK_SCALE_Y = 0.86;
    // 人员间距比例
    public static final double SPACING_SCALE = 0.08;

    public JPanel getJPanel() {
        JPanel panel = new JPanel();
        panel.setName(LanguageEnum.NIU_NIU.getMsg());
        panel.setLayout(null);
        if(HomeJPanel.homeJPanel == null || HomeJPanel.homeJPanel.getHeight() == SwingConstant.WINDOWS_HEIGHT){
            panel.setBounds(SwingConstant.WINDOWS_WEDTH / 8,20,SwingConstant.WINDOWS_WEDTH, SwingConstant.WINDOWS_HEIGHT);
        } else {
            panel.setBounds(HomeJPanel.homeJPanel.getWidth() / 8,20,HomeJPanel.homeJPanel.getWidth(), HomeJPanel.homeJPanel.getHeight());
        }

        // 计算人员间距
        int spacing = (int)(panel.getHeight() * SPACING_SCALE);
        // 取牌
        LinkedList<Brands> brands = BrandUtil.xiPai(false);
        // 结果容器
        List<JSONObject> results = new ArrayList<>();
        // 取出所有人
        LinkedHashMap<String, NnUser> loginUsers = new LinkedHashMap<String, NnUser>(){{
            putAll(LoginUtil.loginUsers);
        }};
        // 发牌
        for (int j = 0; j < NiuNiuUtil.NUMBER_OF_GAMES; j++) {
            JSONObject result = new JSONObject();
            // 字牌容器
            List<Brands> brandsList = new ArrayList<>();
            // 发牌
            for (int i = 0; i < NiuNiuUtil.NIU_NIU_OF_PAGE; i++) {
                // 牌面
                JPanel niu = new JPanel();
                niu.setName(LanguageEnum.NIU_NIU.getMsg());
                niu.setBounds(100 * (i + 1), spacing * j, PAI_WIDTH, PAI_HEIGHT);
                // 随机取牌
                int i1 = RandomUtil.randomInt(0, brands.size() - 1);
                Brands brand = brands.get(i1);
                brandsList.add(brand);
                JLabel label = new JLabel(brand.getName());
                if(brand.getName().startsWith("红")
                    || brand.getName().startsWith("方")){
                    label.setForeground(Color.red);
                }
                label.setIcon(new ImageIcon(brand.getIconFileName()));
                label.setBounds(10, 10,16,16);
                // 添加
                niu.add(label);
                panel.add(niu);
                // 删除已经取出的字牌
                brands.remove(brand);
            }
            // 显示人物
            JPanel figure = new JPanel();
            figure.setName(LanguageEnum.FIGURE.getMsg());
            figure.setBounds(20, spacing * j, PAI_WIDTH, PAI_HEIGHT);

            JLabel ren = new JLabel();
            NnUser user = LoginUtil.getUser(loginUsers);
            if(user != null){
                ren.setText(user.getNikeName());
                ren.setIcon(new ImageIcon(AvatarUtil.avatarFile(user.getAvatar())));
                LoginUtil.delUser(loginUsers);
            } else {
                ren.setText("电脑" + (j + 1));
                ren.setIcon(new ImageIcon("./img/avatar/nan/02.png"));
            }
            ren.setBounds(10, 10, 16, 16);
            figure.add(ren);
            panel.add(figure);
            // 计算显示结果
            JPanel niu = new JPanel();
            niu.setName(LanguageEnum.RESULT.getMsg());
            niu.setBounds(100 * (NiuNiuUtil.NIU_NIU_OF_PAGE + 1), spacing * j, PAI_WIDTH, PAI_HEIGHT);

            result.put("niu",NiuNiuUtil.syncBull(brandsList));
            result.put("brandsList",brandsList);
            result.put("figure",j);
            results.add(result);

            JLabel label = new JLabel(result.getString("niu"));
            label.setForeground(Color.BLUE);
            label.setBounds(10, 10, 16, 16);
            niu.add(label);
            panel.add(niu);
        }
        // 比较大小
        int figure = NiuNiuUtil.compare(results);
        for (int j = 0; j < NiuNiuUtil.NUMBER_OF_GAMES; j++) {
            // 显示胜败
            JPanel niu = new JPanel();
            niu.setName(LanguageEnum.RESULT.getMsg());
            niu.setBounds(100 * (NiuNiuUtil.NIU_NIU_OF_PAGE + 2), spacing * j, PAI_WIDTH, PAI_HEIGHT);
            JLabel label = new JLabel();
            label.setFont(new Font("宋体", Font.PLAIN,20));
            if(figure == j){
//                label.setIcon(new ImageIcon("img/result/胜利.png"));
                label.setForeground(Color.red);
                label.setText(LanguageEnum.WIN.getMsg());
            } else {
//                label.setIcon(new ImageIcon("img/result/负-1.png"));
                label.setText(LanguageEnum.LOSE.getMsg());
            }
            label.setBounds(10, 10, 16, 16);
            niu.add(label);
            panel.add(niu);
        }

        // 创建 JLabel 设置游戏人数
        JLabel userLabel = new JLabel(LanguageEnum.NUMBER_OF_GAMES.getMsg());
        userLabel.setBounds((int)(panel.getWidth() * SINK_SCALE_W) - 150,(int)(panel.getHeight() * SINK_SCALE_Y),BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(userLabel);
        JTextField rsTest = new JTextField(20);
        rsTest.setText(NiuNiuUtil.NUMBER_OF_GAMES+"");
        rsTest.setBounds((int)(panel.getWidth() * SINK_SCALE_W) - 95,(int)(panel.getHeight() * SINK_SCALE_Y),BUTTON_WEDTH_MIN,INPUT_HEIGHT);
        panel.add(rsTest);

        Action action = new TextAction(LanguageEnum.START.getMsg()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int games = NiuNiuUtil.checkGmaes(rsTest.getText());
                if(games > 0){
                    NiuNiuUtil.NUMBER_OF_GAMES = games;
                    if(PropertiesUtil.eqValue("dev","spring.profiles.active")){
                        SwingUtil.switchJpanel(panel,new NiuNiuJPanel().getJPanel());
                    } else {
                        SwingUtil.switchJpanel(panel,SpringContext.getBean(NiuNiuJPanel.class).getJPanel());
                    }
                }
            }
        };

        // 创建开始按钮
        JButton startButton = new JButton(action);
        startButton.setBounds((int)(panel.getWidth() * SINK_SCALE_W), (int)(panel.getHeight() * SINK_SCALE_Y), BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(startButton);
        // 创建返回按钮
        JButton collbackButton = new JButton(LoginJPanel.getReturnAction(panel));
        collbackButton.setBounds((int)(panel.getWidth() * SINK_SCALE_W) - 70, (int)(panel.getHeight() * SINK_SCALE_Y), BUTTON_WEDTH, BUTTON_HEIGHT);
        panel.add(collbackButton);
        return panel;
    }
}
