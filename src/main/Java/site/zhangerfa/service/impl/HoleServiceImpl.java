package site.zhangerfa.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.zhangerfa.dao.HoleMapper;
import site.zhangerfa.pojo.Hole;
import site.zhangerfa.service.HoleNicknameService;
import site.zhangerfa.service.HoleService;

import java.util.List;

@Service
public class HoleServiceImpl implements HoleService {
    @Resource
    private HoleMapper holeMapper;

    @Resource
    private HoleNicknameService holeNicknameService;

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean addHole(Hole hole) {
        // 发布树洞
        int insertNum = holeMapper.addHole(hole);
        // 为树洞主生成随机昵称
        boolean flag = holeNicknameService.addHoleNickname(hole.getId(), hole.getPosterId());
        return insertNum > 0 && flag;
    }

    @Override
    public boolean deleteHoleById(int id) {
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
    public boolean addComment(int holeId, String posterId) {
        int flag = holeMapper.commentNumPlusOne(holeId);
        // 树洞评论还要为其增加随机昵称
        holeNicknameService.addHoleNickname(holeId, posterId);
        return flag > 0;
    }

    @Override
    @Transactional(isolation= Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    public boolean deleteComment(int id) {
        int flag = holeMapper.commentNumMinusOne(id);
        return flag > 0;
    }

    @Override
    public int getNumOfRows() {
        return holeMapper.getNumOfHoles();
    }

    @Override
    public List<Hole> getOnePageHoles(String posterId, int offset, int limit) {
        return holeMapper.selectOnePageHoles(posterId, offset, limit);
    }
}
