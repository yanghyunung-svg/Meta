package com.meta.controller;

import com.common.utils.BizUtils;
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
    public String login(Model model) { return "meta/login"; }
    @GetMapping("/loginLogList")
    public String loginLogList() {  return "meta/loginLogList";  }

    /* 사용자 */
    @GetMapping("/userList")
    public String userList() { return "meta/userList";  }
    @GetMapping("/userReg")
    public String userReg() {  return "meta/userReg";  }
    @GetMapping("/userChg")
    public String userChg() {  return "meta/userChg";  }
    @GetMapping("/userSearch")
    public String userSearch() { return "meta/userSearch";  }
    @GetMapping("/userPasswordChg")
    public String userPasswordChg() { log.debug(BizUtils.logInfo("START")); return "meta/userPasswordChg"; }

    /* 용어 */
    @GetMapping("/termList")
    public String termList() { return "meta/termList"; }
    @GetMapping("/termMng")
    public String termMng() { return "meta/termMng"; }
    @GetMapping("/termRegEblc")
    public String termRegEblc() { return "meta/termRegEblc"; }
    @GetMapping("/termSearch")
    public String termSearch() { return "meta/termSearch"; }

    /* 단어 */
    @GetMapping("/wordList")
    public String wordList() { return "meta/wordList"; }
    @GetMapping("/wordMng")
    public String wordMng() { return "meta/wordMng"; }
    @GetMapping("/wordRegEblc")
    public String wordRegEblc() { return "meta/wordRegEblc"; }

    /* 도메인 */
    @GetMapping("/dmnList")
    public String dmnList() { return "meta/dmnList"; }
    @GetMapping("/dmnMng")
    public String dmnMng() { return "meta/dmnMng"; }
    @GetMapping("/dmnRegEblc")
    public String dmnRegEblc() { return "meta/dmnRegEblc"; }

    /* 공통코드 */
    @GetMapping("/commCodeList")
    public String commCodeList() { return "meta/commCodeList"; }
    @GetMapping("/commCodeGroup")
    public String commCodeGroup() { return "meta/commCodeGroup"; }
    @GetMapping("/commCodeDetl")
    public String commCodeDetl() { return "meta/commCodeDetl"; }

    /* Code Group */
    @GetMapping("/codeGroupList")
    public String codeGroupList() { return "meta/codeGroupList"; }
    @GetMapping("/codeGroupMng")
    public String codeGroupMng() { return "meta/codeGroupMng"; }
    @GetMapping("/codeGroupRegEblc")
    public String codeGroupRegEblc() { return "meta/codeGroupRegEblc"; }
    @GetMapping("/codeGroupSearch")
    public String codeGroupSearch() { return "meta/codeGroupSearch"; }

    /* Code */
    @GetMapping("/codeDetlList")
    public String codeDetlList() { return "meta/codeDetlList"; }
    @GetMapping("/codeDetlMng")
    public String codeDetlMng() { return "meta/codeDetlMng"; }
    @GetMapping("/codeDetlRegEblc")
    public String codeDetlRegEblc() { return "meta/codeDetlRegEblc"; }
    @GetMapping("/codeDetlPopup")
    public String codeDetlPopup() { return "meta/codeDetlPopup"; }

}
