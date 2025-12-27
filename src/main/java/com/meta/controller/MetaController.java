package com.meta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meta")
public class MetaController {

    /* LOGIN */
    @GetMapping("/login")
    public String login(Model model) { return "meta/login"; }
    @GetMapping("/loginLogList")
    public String loginLogListPage() {  return "meta/loginLogList";  }

    /* 용어 */
    @GetMapping("/termList")
    public String termListPage() { return "meta/termList"; }
    @GetMapping("/termMng")
    public String termMngPage() { return "meta/termMng"; }
    @GetMapping("/termRegEblc")
    public String termRegEblcPage() { return "meta/termRegEblc"; }
    @GetMapping("/termSearch")
    public String termSearchPage() { return "meta/termSearch"; }

    /* 단어 */
    @GetMapping("/wordList")
    public String wordListPage() { return "meta/wordList"; }
    @GetMapping("/wordMng")
    public String wordMngPage() { return "meta/wordMng"; }
    @GetMapping("/wordRegEblc")
    public String wordRegEblcPage() { return "meta/wordRegEblc"; }

    /* 도메인 */
    @GetMapping("/dmnList")
    public String dmnListPage() { return "meta/dmnList"; }
    @GetMapping("/dmnMng")
    public String dmnMngPage() { return "meta/dmnMng"; }
    @GetMapping("/dmnRegEblc")
    public String dmnRegEblcPage() { return "meta/dmnRegEblc"; }

    /* 공통코드 */
    @GetMapping("/commCodeList")
    public String commCodeListPage() { return "meta/commCodeList"; }
    @GetMapping("/commCodeGroup")
    public String commCodeGroupPage() { return "meta/commCodeGroup"; }
    @GetMapping("/commCodeDetl")
    public String commCodeDetlPage() { return "meta/commCodeDetl"; }

    /* Code Group */
    @GetMapping("/codeGroupList")
    public String codeGroupListPage() { return "meta/codeGroupList"; }
    @GetMapping("/codeGroupMng")
    public String codeGroupMngPage() { return "meta/codeGroupMng"; }
    @GetMapping("/codeGroupRegEblc")
    public String codeGroupRegEblcPage() { return "meta/codeGroupRegEblc"; }
    @GetMapping("/codeGroupSearch")
    public String codeGroupSearchPage() { return "meta/codeGroupSearch"; }

    /* Code */
    @GetMapping("/codeDetlList")
    public String codeDetlListPage() { return "meta/codeDetlList"; }
    @GetMapping("/codeDetlMng")
    public String codeDetlMngPage() { return "meta/codeDetlMng"; }
    @GetMapping("/codeDetlRegEblc")
    public String codeDetlRegEblcPage() { return "meta/codeDetlRegEblc"; }
    @GetMapping("/codeDetlPopup")
    public String codeDetlPopupPage() { return "meta/codeDetlPopup"; }

    /* 사용자 */
    @GetMapping("/userList")
    public String userListPage() { return "meta/userList";  }
    @GetMapping("/userReg")
    public String userRegPage() {  return "meta/userReg";  }
    @GetMapping("/userChg")
    public String userChgPage() {  return "meta/userChg";  }
    @GetMapping("/userSearch")
    public String userSearchPage() { return "meta/userSearch";  }
    @GetMapping("/userPasswordChg")
    public String userPasswordChgPage() { return "meta/userPasswordChg"; }
    @GetMapping("/userRegEblc")
    public String userRegEblcPage() { return "meta/userRegEblc"; }

    /* 신청.승인 */
    @GetMapping("/aplyDsctnInq")
    public String aplyDsctnInqPage() { return "meta/aplyDsctnInq"; }
    @GetMapping("/aprvDsctnInq")
    public String aprvDsctnInqPage() { return "meta/aprvDsctnInq"; }

    /* 조회 */
    @GetMapping("/commCodeSearch")
    public String commCodeSearchPage() { return "meta/commCodeSearch"; }

}
