package com.meta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meta")
public class MetaController {

    /* LOGIN */
    @GetMapping("/intro")
    public String intro(Model model) { return "meta/intro"; }
    @GetMapping("/login")
    public String login(Model model) { return "meta/login"; }
    @GetMapping("/comm/loginLogList")
    public String loginLogListPage() {  return "meta/comm//loginLogList";  }

    /* 도메인 */
    @GetMapping("/dmn/dmnList")
    public String dmnListPage() { return "meta/dmn/dmnList"; }
    @GetMapping("/dmn/dmnMng")
    public String dmnMngPage() { return "meta/dmn/dmnMng"; }
    @GetMapping("/dmn/dmnRegEblc")
    public String dmnRegEblcPage() { return "meta/dmn/dmnRegEblc"; }
    @GetMapping("/popup/dmnSearch")
    public String dmnSearchPage() { return "meta/popup/dmnSearch"; }

    /* 공통코드 */
    @GetMapping("/code/commCodeList1")
    public String commCodeList1Page() { return "meta/code/commCodeList1"; }
    @GetMapping("/code/commCodeList")
    public String commCodeListPage() { return "meta/code/commCodeList"; }
    @GetMapping("/code/commCodeGroup")
    public String commCodeGroupPage() { return "meta/code/commCodeGroup"; }
    @GetMapping("/code/commCodeDetl")
    public String commCodeDetlPage() { return "meta/code/commCodeDetl"; }

    /* Code Group */
    @GetMapping("/code/codeGroupList")
    public String codeGroupListPage() { return "meta/code/codeGroupList"; }
    @GetMapping("/code/codeGroupMng")
    public String codeGroupMngPage() { return "meta/code/codeGroupMng"; }
    @GetMapping("/code/codeGroupRegEblc")
    public String codeGroupRegEblcPage() { return "meta/code/codeGroupRegEblc"; }

    @GetMapping("/popup/codeGroupSearch")
    public String codeGroupSearchPage() { return "meta/popup/codeGroupSearch"; }

    /* Code Detl */
    @GetMapping("/code/codeDetlList")
    public String codeDetlListPage() { return "meta/code/codeDetlList"; }
    @GetMapping("/code/codeDetlMng")
    public String codeDetlMngPage() { return "meta/code/codeDetlMng"; }
    @GetMapping("/code/codeDetlRegEblc")
    public String codeDetlRegEblcPage() { return "meta/code/codeDetlRegEblc"; }
    @GetMapping("/code/codeDetlListPopup")
    public String codeDetlListPopup() { return "meta/code/codeDetlListPopup"; }

    /* 사용자 */
    @GetMapping("/user/userList")
    public String userListPage() { return "meta/user/userList";  }
    @GetMapping("/user/userReg")
    public String userRegPage() {  return "meta/user/userReg";  }
    @GetMapping("/user/userChg")
    public String userChgPage() {  return "meta/user/userChg";  }
    @GetMapping("/user/userRegEblc")
    public String userRegEblcPage() { return "meta/user/userRegEblc"; }
    @GetMapping("/popup/userSearch")
    public String userSearchPage() { return "meta/popup/userSearch";  }

    /* 신청.승인 */
    @GetMapping("/comm/aplyDsctnInq")
    public String aplyDsctnInqPage() { return "meta/comm/aplyDsctnInq"; }
    @GetMapping("/comm/aprvDsctnInq")
    public String aprvDsctnInqPage() { return "meta/comm/aprvDsctnInq"; }

    /* 조회 */
    @GetMapping("/code/commCodeSearch")
    public String commCodeSearchPage() { return "meta/code/commCodeSearch"; }
}
