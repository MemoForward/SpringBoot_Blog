package com.memoforward.blog.controller;

import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.model.Tag;
import com.memoforward.blog.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    ITagService tagService;


    @Value("${page.limit.tag}")
    private Integer PAGELIMITTAG;



    @GetMapping("/tags")
    public String manageTags(@RequestParam(value = "eMessage", required = false) String eMessage,
                             @RequestParam(value = "sMessage", required = false) String sMessage,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             Model model) {
        if (eMessage != null) model.addAttribute("eMessage", eMessage);
        if (sMessage != null) model.addAttribute("sMessage", sMessage);
        PageDto<Tag> tagPage = tagService.listTags(pageNum, PAGELIMITTAG);
        model.addAttribute("tagPage", tagPage);
        return "admin/tags";
    }

    @GetMapping("/addNewTag")
    public String addNewTag(@RequestParam("newTag") String newTag,
                            RedirectAttributes redirectAttributes) {
        if (newTag == null || "".equals(newTag)) {
            redirectAttributes.addFlashAttribute("eMessage", "标签不能为空!");
        } else {
            Tag tag = tagService.findTagByTagName(newTag);
            if (tag == null) {
                tagService.saveTag(newTag);
                redirectAttributes.addFlashAttribute("sMessage", "添加成功!");
                redirectAttributes.addAttribute("pageNum", 999);
            } else {
                redirectAttributes.addFlashAttribute("eMessage", "标签已存在！");
            }
        }
        System.out.println("请求成功！");
        return "redirect:/admin/tags";
    }

    @GetMapping("/deleteTag")
    public String deleteTag(@RequestParam("tagId") Long tagId,
                            @RequestParam("pageNum") Integer pageNum,
                            RedirectAttributes redirectAttributes) {
//        System.out.println(pageNum);
        tagService.removeTagById(tagId);
        redirectAttributes.addAttribute("pageNum", pageNum);
        return "redirect:/admin/tags";
    }




}
