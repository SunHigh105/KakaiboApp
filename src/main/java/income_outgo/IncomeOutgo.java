package income_outgo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Entity // This tells Hibernate to make a table out of this class
public class IncomeOutgo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long cost;
    private Date date;
    private String memo;
    private String type;
    private Long category_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getDate() {
       SimpleDateFormat StringDate = new SimpleDateFormat("yyyy-MM-dd");
       return StringDate.format(date);
    }

    public void setDate(String date) {
        this.date = Date.valueOf(date);
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }


}