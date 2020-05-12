package com.cluster.digital.utils;

import com.cluster.digital.database.entity.*;
import com.cluster.digital.model.request.*;
import org.springframework.util.StringUtils;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-11
 */
public class BeanUtils {

    public static Cluster copyBeanProperties(ClusterDTORequest from, Cluster to) {
        if (!StringUtils.isEmpty(from.getName())) {
            to.setName(from.getName());
        }
        if (!StringUtils.isEmpty(from.getState())) {
            to.setState(from.getState());
        }
        if (!StringUtils.isEmpty(from.getDistrict())) {
            to.setDistrict(from.getDistrict());
        }
        return to;
    }

    public static Dairy copyBeanProperties(DairyDTORequest request, Dairy dairy) {
        dairy.setName(request.getName());
        dairy.setDistrict(request.getDistrict());
        dairy.setState(request.getState());
        return dairy;
    }

    public static Route copyBeanProperties(RouteDTORequest routeDTORequest, Route route) {
        route.setName(routeDTORequest.getName());
        return route;
    }

    public static Mcc copyBeanProperties(MccDTORequest request, Mcc mcc) {
        mcc.setName(request.getName());
        mcc.setAddress(request.getAddress());
        mcc.setPincode(request.getPincode());
        mcc.setVillage(request.getVillage());
        mcc.setTaluk(request.getTaluk());
        mcc.setLatitude(request.getLatitude());
        mcc.setLongitude(request.getLongitude());
        return mcc;
    }

    public static FieldExecutive copyBeanProperties(FieldExecutiveDTORequest request, FieldExecutive fieldExecutive) {
        fieldExecutive.setName(request.getName());
        fieldExecutive.setMobile(request.getMobile());
        return fieldExecutive;
    }
}
