package com.openlab.statistic.controller;


import com.openlab.statistic.entity.ScreenDetail;
import com.openlab.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
@CrossOrigin
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/screen")
    public ScreenDetail screen(@RequestParam("community_id") Long communityId) throws ParseException {
        return statisticService.getScreenDetail(communityId);
    }
}
