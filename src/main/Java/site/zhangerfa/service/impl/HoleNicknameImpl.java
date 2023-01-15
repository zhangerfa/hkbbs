package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.HoleNicknameMapper;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.util.Constant;

import java.util.*;

@Service
public class HoleNicknameImpl implements HoleNicknameService {
    @Resource
    private HoleNicknameMapper holeNicknameMapper;

    @Override
    public boolean addHoleNickname(int holeId, String stuId) {
        // 获取该树洞的所有随机昵称
        String[] nicknames = holeNicknameMapper.selectAllHoleNickname4Hole(holeId);
        Set<String> nicknamesSet = Set.of(nicknames);
        // 生成一个未被当前树洞使用的随机昵称
        String nicknameIndex = "";
        do {
            // 生成字符索引
            Random random = new Random();
            nicknameIndex += random.nextInt(Constant.FIRSTNAME.length);
            nicknameIndex += (";" + random.nextInt(Constant.SECONDNAME.length));
        } while (nicknamesSet.contains(nicknameIndex));
        int insertNum = holeNicknameMapper.insertHoleNickname(holeId, stuId, nicknameIndex);
        return insertNum > 0;
    }

    @Override
    public String getHoleNickname(int holeId, String stuId) {
        // 获取树洞昵称索引
        String nicknameIndex = holeNicknameMapper.selectHoleNickname(holeId, stuId);
        String[] indexArray = nicknameIndex.split(";");
        // 从字符集中获取字符
        String nickname = "";
        nickname += Constant.FIRSTNAME[Integer.parseInt(indexArray[0])];
        nickname += Constant.SECONDNAME[Integer.parseInt(indexArray[1])];

        return nickname;
    }
}
