package quanpnph29471.example.quanpnph29471_asm.Model;

public class Task {
    int id,status;
    String name,content,start,end;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Task(int id, int status, String name, String content, String start, String end) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.content = content;
        this.start = start;
        this.end = end;
    }

    public Task(int status, String name, String content, String start, String end) {
        this.status = status;
        this.name = name;
        this.content = content;
        this.start = start;
        this.end = end;
    }

    public String getStatus1(int status){
        if(status == 0){
            return "Mới tạo";
        } else if(status == 1){
            return "Đang làm";
        }else if(status == 2){
            return "Hoàn thành";
        }else  {
            return "Hoạt động đã bị hủy";
        }
    }

}
