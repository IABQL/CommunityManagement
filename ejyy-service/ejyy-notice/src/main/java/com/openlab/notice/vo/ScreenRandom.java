package com.openlab.notice.vo;


import com.openlab.notice.Entity.Notice;
import com.openlab.notice.Entity.ScreenDetail;

import java.util.Random;

public class ScreenRandom {
    public static Random random=new Random();
    public static ScreenDetail random(Notice notice){
        ScreenDetail screenDetail = new ScreenDetail();
        screenDetail.setBuilding_total(random.nextInt(99));
        screenDetail.setCar_total(random.nextInt(99));
        screenDetail.setPet_total(random.nextInt(99));
        screenDetail.setRepair_total(random.nextInt(99));
        screenDetail.setComplain_total(random.nextInt(99));
        screenDetail.setMovecar_total(random.nextInt(99));
        screenDetail.setCpu(random.nextInt(99));
        screenDetail.setMem(random.nextInt(99));
        screenDetail.setDisk(random.nextInt(99));
        screenDetail.setNotice(notice);
        return screenDetail;
    }
}
