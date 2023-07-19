package site.zhangerfa.service;

/**
 * 统计网站访问量等数据
 */
public interface WebDataService {
    /**
     * pv+1
     * @param
     * @return
     */
    void addPv();

    /**
     * 获取访问量
     * @return
     */
    Integer getPv();

    /**
     * 获取访客数量
     * @return
     */
    Integer getUv();

    /**
     * 增加访问量
     */
    void addUv(String stuId);
}
