package com.memoforward.blog.controller;

import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.model.Type;
import com.memoforward.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminTypeController {

    @Autowired
    ITypeService typeService;

    @Value("${page.limit.type}")
    private Integer PAGELIMITTYPE;


    @GetMapping("/types")
    public String manageTypes(@RequestParam(value = "eMessage", required = false) String eMessage,
                             @RequestParam(value = "sMessage", required = false) String sMessage,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             Model model) {
        if (eMessage != null) model.addAttribute("eMessage", eMessage);
        if (sMessage != null) model.addAttribute("sMessage", sMessage);
        PageDto<Type> typePage = typeService.listTypes(pageNum, PAGELIMITTYPE);
        model.addAttribute("typePage", typePage);
        return "admin/types";
    }

    @GetMapping("/addNewType")
    public String addNewType(@RequestParam("newType") String newType,
                             RedirectAttributes redirectAttributes) {
        if (newType == null || "".equals(newType)) {
            redirectAttributes.addFlashAttribute("eMessage", "类别不能为空!");
        } else {
            Type type = typeService.findTypeByTypeName(newType);
            if (type == null) {
                typeService.saveType(newType);
                redirectAttributes.addFlashAttribute("sMessage", "添加成功!");
                redirectAttributes.addAttribute("pageNum", 999);
            } else {
                redirectAttributes.addFlashAttribute("eMessage", "类别已存在！");
            }
        }
        System.out.println("请求成功！");
        return "redirect:/admin/types";
    }

    @GetMapping("/deleteType")
    public String deleteType(@RequestParam("typeId") Long typeId,
                             @RequestParam("pageNum") Integer pageNum,
                             RedirectAttributes redirectAttributes) {
//        System.out.println(pageNum);
        typeService.removeTypeById(typeId);
        redirectAttributes.addAttribute("pageNum", pageNum);
        return "redirect:/admin/types";
    }

}
