package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.zhangerfa.dao.PostMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.pojo.Post;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.HoleService;

import java.util.List;
import java.util.Map;

@Service
public class HoleServiceImpl extends PostServiceImpl implements HoleService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private HoleNicknameService holeNicknameService;

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean add(Post hole) {
        super.add(hole);
        // 发布树洞
        int insertNum = postMapper.addHole(hole);
        // 为洞主生成随机昵称
        boolean flag = holeNicknameService.addHoleNickname(hole.getId(), hole.getPosterId());
        return insertNum > 0 && flag;
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public Map<String, Object> deleteById(int id) {
        // 删除所有该树洞的随机昵称
        holeNicknameService.deleteNicknamesForHole(id);
        return super.deleteById(id);
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean addComment(Comment comment, int holeId) {
        boolean flag = super.addComment(comment, holeId);
        // 生成随机昵称
        flag &= holeNicknameService.addHoleNickname(holeId, comment.getPosterId());
        return flag;
    }

    @Override
    public int getTotalNums() {
        return postMapper.getNumOfHoles();
    }

    @Override
    public List<Hole> getOnePageHoles(String posterId, int offset, int limit) {
        return postMapper.selectOnePageHoles(posterId, offset, limit);
    }
}
