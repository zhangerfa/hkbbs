package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import site.zhangerfa.dao.HoleNicknameMapper;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.Constant.Constant;

import java.util.*;

@Service
public class HoleNicknameImpl implements HoleNicknameService {
    @Resource
    private HoleNicknameMapper holeNicknameMapper;

    /**
     *
     * @param holeId 树洞id
     * @param posterId 用户学号
     *    生成的构成昵称的字符索引：nicknameIndex 构成昵称的字符在字符集中的索引，昵称由多个字符组成，
     *                      每个字符有其索引，索引之间使用 ； 分割
     * @return
     */
    @Override
    public boolean addHoleNickname(int holeId, String posterId) {
        // 先判断该用户是否已经在该树洞发过帖
        String nickname = holeNicknameMapper.selectHoleNickname(holeId, posterId);
        if (nickname != null){
            return true;
        }
        // 获取该树洞的所有随机昵称
        String[] nicknames = holeNicknameMapper.selectAllHoleNickname4Hole(holeId);
        Set<String> nicknamesSet = Set.of(nicknames);
        // 生成一个未被当前树洞使用的随机昵称
        String nicknameIndex = "";
        do {
            // 生成字符索引
            Random random = new Random();
            nicknameIndex += random.nextInt(Constant.FIRST_NAME.length);
            nicknameIndex += (";" + random.nextInt(Constant.SECOND_NAME.length));
        } while (nicknamesSet.contains(nicknameIndex));
        int insertNum = holeNicknameMapper.insertHoleNickname(holeId, posterId, nicknameIndex);
        return insertNum > 0;
    }

    /**
     * 获取用户在传入树洞的昵称
     *    发布、评论树洞时不生成随机昵称，第一次查询时生成
     * @param holeId 树洞id
     * @param posterId 用户学号
     * @return 返回昵称
     */
    @Override
    public String getHoleNickname(int holeId, String posterId) {
        // 获取树洞昵称索引
        String nicknameIndex = holeNicknameMapper.selectHoleNickname(holeId, posterId);
        if (nicknameIndex == null){
            // 当查询次用户在树洞的随机昵称时发现未生成随机昵称，则生成
            addHoleNickname(holeId, posterId);
            nicknameIndex = holeNicknameMapper.selectHoleNickname(holeId, posterId);
        }
        String[] indexArray = nicknameIndex.split(";");
        // 从字符集中获取字符

        return "" + Constant.FIRST_NAME[Integer.parseInt(indexArray[0])] +
                Constant.SECOND_NAME[Integer.parseInt(indexArray[1])];
    }

    @Override
    public boolean deleteNicknamesForHole(int holeId) {
        int deleteNum = holeNicknameMapper.deleteNicknamesByHoleId(holeId);
        return deleteNum > 0;
    }
}
