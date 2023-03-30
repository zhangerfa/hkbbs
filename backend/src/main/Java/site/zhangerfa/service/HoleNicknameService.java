package site.zhangerfa.service;

public interface HoleNicknameService {
    /**
     * 为传入树洞中的用户添加一条随机的昵称
     * @param holeId 树洞id
     * @param stuId 用户学号
     *    生成的构成昵称的字符索引：nicknameIndex 构成昵称的字符在字符集中的索引，昵称由多个字符组成，
     *                      每个字符有其索引，索引之间使用 ； 分割
     * @return 是否添加成功
     */
    boolean addHoleNickname(int holeId, String stuId);

    /**
     * 获取传入树洞中的用户的昵称
     * @param holeId 树洞id
     * @param stuId 用户学号
     * @return 昵称
     */
    String getHoleNickname(int holeId, String stuId);

    /**
     * 删除该树洞的所有随机昵称
     * @param holeId 树洞id
     * @return
     */
    boolean deleteNicknamesForHole(int holeId);
}
