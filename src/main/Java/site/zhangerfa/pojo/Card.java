package site.zhangerfa.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import site.zhangerfa.controller.tool.NewPost;

@Schema(description = "卡片")
public class Card extends Post{
    public Card(){};
    public Card(NewPost post){
        super(post);
    }
}
