package com.dl.mjapp.controller;

import com.dl.common.model.entity.DlOpenlog;
import com.dl.common.pojo.DlResponse;
import com.dl.mjapp.service.IOpenlogService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "openlog")
public class DlOpenlogController extends BaseController {

    @Autowired
    private IOpenlogService dlOpenlogService;

    @PostMapping(value = "upload")
	@ApiOperation("upload openlog")
    public DlResponse upload(DlOpenlog openlog) {
        return DlResponse.ok();
    }

    @PostMapping(value = "uploadList")
	@ApiOperation("upload Multiple openlog")
    public DlResponse uploadList(HttpServletRequest req) {
        return DlResponse.ok();
    }

}
