package site.zhangerfa.service;

/**
 * 统计网站访问量等数据
 */
public interface WebDataService {
    /**
     * 获取网站今日访问量
     * @return 网站访问量
     */
    public int getPv();

    /**
     * 增加网站今日访问量
     */
    public void addPvForToday();
}
