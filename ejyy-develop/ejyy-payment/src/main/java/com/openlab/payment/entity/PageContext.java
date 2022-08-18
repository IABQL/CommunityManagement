package com.openlab.payment.entity;

import lombok.Data;

@Data
public class PageContext {
    Integer current_page;
    Integer page_size;
    Integer order_state;
}
