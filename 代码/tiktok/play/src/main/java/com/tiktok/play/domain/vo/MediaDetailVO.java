package com.tiktok.play.domain.vo;

import com.tiktok.play.domain.po.Production;
import java.util.List;

public class MediaDetailVO {
    private Production production;
    public MediaDetailVO(Production production) {
        this.production=production;
    }

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }
}
