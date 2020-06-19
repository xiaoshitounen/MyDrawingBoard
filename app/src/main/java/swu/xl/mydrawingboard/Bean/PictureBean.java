package swu.xl.mydrawingboard.Bean;

public class PictureBean {
    //画的id
    private long id;
    //画的名称
    private String name;
    //画的保存路径
    private String path;
    //画的创建日期
    private String date;
    //是否显示删除标志
    private boolean delete = false;

    /**
     * 构造方法
     * @param id
     * @param name
     * @param path
     * @param date
     */
    public PictureBean(int id, String name, String path, String date) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.date = date;
    }

    /**
     * 打印
     * @return
     */
    @Override
    public String toString() {
        return "PictureBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    //setter，getter方法
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
