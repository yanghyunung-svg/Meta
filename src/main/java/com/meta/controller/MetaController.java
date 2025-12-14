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
    @GetMapping("/termRegEblc")
    public String termRegEblcView() { return "meta/termRegEblc"; }
    @GetMapping("/termEdit")
    public String termEditView() { return "meta/termEdit"; }

    /* 단어 */
    @GetMapping("/wordSearch")
    public String wordSearchView() { return "meta/wordSearch"; }
    @GetMapping("/wordReg")
    public String wordRegView() { return "meta/wordReg"; }
    @GetMapping("/wordChg")
    public String wordChgView() { return "meta/wordChg"; }

    /* 도메인 */
    @GetMapping("/dmnSearch")
    public String dmnSearchView() { return "meta/dmnSearch"; }
    @GetMapping("/dmnReg")
    public String dmnRegView() { return "meta/dmnReg"; }
    @GetMapping("/dmnChg")
    public String dmnChgView() { return "meta/dmnChg"; }

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
    @GetMapping("/codeDetlRegEblc")
    public String codeDetlRegEblcView() { return "meta/codeDetlRegEblc"; }

}
