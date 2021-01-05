package com.hugovallada.bookstoremanager.publishers.controller;

import com.hugovallada.bookstoremanager.publishers.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PublisherController {

    private PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }
}
