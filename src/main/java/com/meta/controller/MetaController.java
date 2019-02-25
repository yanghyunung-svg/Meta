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

    /* 사용자 */
    @GetMapping("/login")
    public String loginView(Model model) { return "meta/login"; }
    @GetMapping("/userSearch")
    public String getUserSearchView() {  return "meta/userSearch";  }
    @GetMapping("/userReg")
    public String getUserRegView() {   return "meta/userReg";  }

    /* dashboard */
    @GetMapping("/dashboard")
    public String dashboardView(Model model) { return "meta/dashboard"; }

    /* 용어 */
    @GetMapping("/termSearch")
    public String getTermSearchView() { return "meta/termSearch"; }
    @GetMapping("/termReg")
    public String getTermRegView() { return "meta/termReg"; }
    @GetMapping("/termRegTlgm")
    public String getTermRegTlgmView() { return "meta/termRegTlgm"; }

    /* 단어 */
    @GetMapping("/wordSearch")
    public String getWordSearchView() { return "meta/wordSearch"; }
    @GetMapping("/wordReg")
    public String getWordRegView() { return "meta/wordReg"; }

    /* Code Group */
    @GetMapping("/codeGroupSearch")
    public String getCodeGroupSearchView() { return "meta/codeGroupSearch"; }
    @GetMapping("/codeGroupReg")
    public String getCodeGroupRegView() { return "meta/codeGroupReg"; }
    @GetMapping("/codeGroupPopup")
    public String getCodeGroupPopupView() { return "meta/codeGroupPopup"; }

    /* Code */
    @GetMapping("/codeSearch")
    public String getCodeSearchView() { return "meta/codeSearch"; }
    @GetMapping("/codeSearchPopup")
    public String getCodeSearchPopupView() { return "meta/codeSearchPopup"; }
    @GetMapping("/codeReg")
    public String getCodeRegView() { return "meta/codeReg"; }

    /* 테이블 */
    @GetMapping("/tableSearch")
    public String getTableSearchView() { return "meta/tableSearch"; }
    @GetMapping("/tableReg")
    public String getTableRegView() { return "meta/tableReg"; }

    /* 컬럼 */
    @GetMapping("/columnSearch")
    public String getColumnSearchView() { return "meta/columnSearch"; }
    @GetMapping("/columnReg")
    public String getcolumnRegView() { return "meta/columnReg"; }


}
