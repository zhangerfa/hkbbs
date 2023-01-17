package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.zhangerfa.dao.HoleMapper;
import site.zhangerfa.pojo.Comment;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.service.CommentService;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.HoleService;
import site.zhangerfa.util.Constant;

import java.util.List;
import java.util.Map;

@Service
public class HoleServiceImpl implements HoleService {
    @Resource
    private HoleMapper holeMapper;
    @Resource
    private CommentService commentService;
    @Resource
    private HoleNicknameService holeNicknameService;

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean add(Hole hole) {
        // 发布树洞
        int insertNum = holeMapper.addHole(hole);
        // 为树洞主生成随机昵称
        boolean flag = holeNicknameService.addHoleNickname(hole.getId(), hole.getPosterId());
        return insertNum > 0 && flag;
    }

    @Override
    public boolean deleteById(int id) {
        // 删除所有该树洞的随机昵称
        holeNicknameService.deleteNicknamesForHole(id);
        // 删除树洞
        int deleteNum = holeMapper.deleteHoleById(id);
        return deleteNum > 0;
    }

    @Override
    public Hole getHoleById(int id) {
        return holeMapper.selectHoleById(id);
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean addComment(Comment comment, int holeId) {
        // 发布评论
        boolean flag = commentService.addComment(comment);
        // 卡片表中评论数量加一
        flag &= commentNumPlusOne(comment.getEntityId());
        // 生成随机昵称
        flag &= holeNicknameService.addHoleNickname(holeId, comment.getStuId());
        return flag;
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public Map<String, Object> deleteComment(int commentId) {
        // 卡片表评论数减一
        Comment comment = commentService.getCommentById(commentId);
        commentNumMinusOne(comment.getEntityId());
        // 删除评论
        return commentService.deleteComment(commentId);
    }

    @Override
    public List<Comment> getComments(int id) {
        return getComments(id, 0, Integer.MAX_VALUE);
    }

    @Override
    public List<Comment> getComments(int id, int offset, int limit) {
        return commentService.getCommentsForEntity(Constant.ENTITY_TYPE_HOLE, id, offset, limit);
    }

    @Override
    public int getTotalNums() {
        return holeMapper.getNumOfHoles();
    }

    @Override
    public List<Hole> getOnePageHoles(String posterId, int offset, int limit) {
        return holeMapper.selectOnePageHoles(posterId, offset, limit);
    }

    public boolean commentNumPlusOne(int cardId) {
        int flag = holeMapper.commentNumPlusOne(cardId);
        return flag > 0;
    }

    public boolean commentNumMinusOne(int cardId) {
        int flag = holeMapper.commentNumMinusOne(cardId);
        return flag > 0;
    }
}
