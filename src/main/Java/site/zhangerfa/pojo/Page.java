package site.zhangerfa.pojo;

/**
 * 封装分页信息
 */
public class Page {
    private static final int maxNumOfPageOnPage = 50; // 一页显示的最大卡片数量

    // 当前页码
    private int current = 1;
    // 一页卡片的数量
    private int numOfPageOnPage = 10;
    // 总的卡片数（用于计算总共有多少页，便于前端显示分页按钮）
    private int rows = -1; // 默认值为-1，表示还未设置总卡片数
    // 查询路径
    private String path;

    /**
     * 返回当前页首行
     * @return
     */
    public int getOffset(){
        return (current - 1) * numOfPageOnPage;
    }

    /**
     * 返回当前页最后一行
     * @return
     */
    public int getLimit(){
        return getOffset() + numOfPageOnPage;
    }

    /**
     * 获取当前总的页数
     * @return
     */
    public int getTotal() throws Exception {
        int total = getRows() / numOfPageOnPage;
        total = (rows % numOfPageOnPage) == 0? total: total + 1;
        return total;
    }

    /**
     * 获取起始页码，当前页的前两页，不会小于 1
     * @return
     */
    public int getFrom(){
        return Math.max(current - 2, 1);
    }

    /**
     * 获取终止页码，当前页后两页，不会大于最大页数
     * @return
     */
    public int getTo() throws Exception {
        int total = getTotal();
        return Math.min(current + 2, total);
    }

    public int getCurrent() {
        return current;
    }

    /**
     * 当传入负数则不改变当前页码
     * @param current
     */
    public void setCurrent(int current) {
        if (current >= 1) this.current = current;
    }

    public int getNumOfPageOnPage() {
        return numOfPageOnPage;
    }

    /**
     * 一页卡片数量不超过规定的最大数量
     * @param numOfPageOnPage
     */
    public void setNumOfPageOnPage(int numOfPageOnPage) {
        if (numOfPageOnPage >= 1 && numOfPageOnPage <= maxNumOfPageOnPage){
            this.numOfPageOnPage = numOfPageOnPage;
        }
    }

    /**
     * 当获取最大行数但还未设置时抛出异常
     * @return
     * @throws Exception
     */
    public int getRows() throws Exception {
        if (rows == -1){
            throw new Exception("未设置最大行数");
        }
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
