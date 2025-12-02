package com.meta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meta")
public class MetaController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public String loginVIew(Model model) { return "meta/login"; }

    @GetMapping("/dashboard")
    public String dashboardView(Model model) { return "meta/dashboard"; }

    /* 용어 */
    @GetMapping("/termSearch")
    public String getTermSearchPage() { return "meta/termSearch"; }
    @GetMapping("/termReg")
    public String getTermRegPage() { return "meta/termReg"; }

    /* 단어 */
    @GetMapping("/wordSearch")
    public String getWordSearchPage() { return "meta/wordSearch"; }
    @GetMapping("/wordReg")
    public String getWordRegPage() { return "meta/wordReg"; }


    /* 테이블 */
    @GetMapping("/tableSearch")
    public String getTableSearchPage() { return "meta/tableSearch"; }
    @GetMapping("/tableReg")
    public String getTableRegPage() { return "meta/tableReg"; }
    /* 컬럼 */
    @GetMapping("/columnSearch")
    public String getColumnSearchPage() { return "meta/columnSearch"; }

    @GetMapping("/columnReg")
    public String getcolumnRegPage() { return "meta/columnReg"; }

    /* 사용자 */
    @GetMapping("/userSearch")
    public String getUserSearchPage() {  return "meta/userSearch";  }
    @GetMapping("/userReg")
    public String getUserRegPage() {   return "meta/userReg";  }

    /* Code Group */
    @GetMapping("/codeGroupSearch")
    public String getCodeGroupSearchPage() { return "meta/codeGroupSearch"; }
    @GetMapping("/codeGroupReg")
    public String getCodeGroupRegPage() { return "meta/codeGroupReg"; }
    @GetMapping("/codeGroupPopup")
    public String getCodeGroupPopupPage() { return "meta/codeGroupPopup"; }

    /* Code */
    @GetMapping("/codeSearch")
    public String getCodeSearchPage() { return "meta/codeSearch"; }
    @GetMapping("/codeReg")
    public String getCodeRegPage() { return "meta/codeReg"; }


}
