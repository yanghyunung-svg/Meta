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

    /* LOGIN */
    @GetMapping("/login")
    public String loginView(Model model) { return "meta/login"; }
    @GetMapping("/loginLogSearch")
    public String loginLogSearchView() {   return "meta/loginLogSearch";  }

    /* 사용자 */
    @GetMapping("/userSearch")
    public String userSearchView() {  return "meta/userSearch";  }
    @GetMapping("/userReg")
    public String userRegView() {   return "meta/userReg";  }
    @GetMapping("/userChg")
    public String userChgView() {   return "meta/userChg";  }
    @GetMapping("/userPopup")
    public String userPopupView() {  return "meta/userPopup";  }

    /* 용어 */
    @GetMapping("/termSearch")
    public String termSearchView() { return "meta/termSearch"; }
    @GetMapping("/termReg")
    public String termRegView() { return "meta/termReg"; }
    @GetMapping("/termChg")
    public String termChgView() { return "meta/termChg"; }

    /* 단어 */
    @GetMapping("/wordSearch")
    public String wordSearchView() { return "meta/wordSearch"; }
    @GetMapping("/wordReg")
    public String wordRegView() { return "meta/wordReg"; }
    @GetMapping("/wordChg")
    public String wordChgView() { return "meta/wordChg"; }

    /* Code Group */
    @GetMapping("/codeGroupSearch")
    public String codeGroupSearchView() { return "meta/codeGroupSearch"; }
    @GetMapping("/codeGroupReg")
    public String codeGroupRegView() { return "meta/codeGroupReg"; }
    @GetMapping("/codeGroupChg")
    public String codeGroupChgView() { return "meta/codeGroupChg"; }
    @GetMapping("/codeGroupPopup")
    public String codeGroupPopupView() { return "meta/codeGroupPopup"; }

    /* Code */
    @GetMapping("/codeDetlSearch")
    public String codeDetlSearchView() { return "meta/codeDetlSearch"; }
    @GetMapping("/codeDetlReg")
    public String codeDetlRegView() { return "meta/codeDetlReg"; }
    @GetMapping("/codeDetlChg")
    public String codeDetlChgView() { return "meta/codeDetlChg"; }
    @GetMapping("/codeDetlPopup")
    public String codeDetlPopupView() { return "meta/codeDetlPopup"; }

}
