package site.zhangerfa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import site.zhangerfa.controller.tool.Code;
import site.zhangerfa.controller.tool.Result;
import site.zhangerfa.pojo.Manager;
import site.zhangerfa.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/manager")
@Tag(name = "管理员")
public class ManagerController {
    @Resource
    private ManagerService managerService;


    @Operation(summary = "添加管理员", description = "添加管理员")
    @PostMapping("/add")
    public Result<Boolean> addManager(String stuId, int level){
        return managerService.addManager(new Manager(stuId, level));
    }

    @Operation(summary = "删除管理员", description = "删除管理员")
    @DeleteMapping("/delete")
    public Result<Boolean> deleteManager(String stuId){
        return managerService.deleteManager(stuId);
    }

    @Operation(summary = "获取管理员列表", description = "获取管理员列表")
    @GetMapping("/list")
    public Result<List<Manager>> getManagerList(){
        List<Manager> managerList = managerService.getManagerList();
        int code = managerList == null ? Code.GET_ERR: Code.GET_OK;
        return new Result<>(code, managerList);
    }

    @Operation(summary = "获取今天访问量", description = "获取今天访问量")
    @GetMapping("/pv")
    public Result<Integer> getPvForToday(){
        return new Result<>(Code.GET_OK, managerService.getPv());
    }
}
