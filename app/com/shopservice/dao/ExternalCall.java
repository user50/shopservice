package com.shopservice.dao;

/**
 * Created by Yevhen on 1/4/15.
 */
public interface ExternalCall<R,Q> {

    R execute(Q query);
}
