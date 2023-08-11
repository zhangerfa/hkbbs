package site.zhangerfa.controller.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UpdateCard extends BaseCard{
    @JsonIgnore
    private int goal;
}
