package com.cluster.digital.service;

import com.cluster.digital.database.entity.*;
import com.cluster.digital.database.repo.*;
import com.cluster.digital.exception.IdDoesNotExistsException;

import java.util.function.Supplier;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-08
 */
public interface BaseInterface {
    default Cluster check4ClusterExistence(ClusterRepository clusterRepository, String cluster) throws Throwable {
        return clusterRepository.findById(cluster).orElseThrow((Supplier<Throwable>) () -> new IdDoesNotExistsException("No cluster found with id: " + cluster));
    }

    default Dairy check4DairyExistence(DairyRepository dairyRepository, String dairyId) throws Throwable {
        return dairyRepository.findById(dairyId).orElseThrow((Supplier<Throwable>) () -> new IdDoesNotExistsException("No dairy found with id: " + dairyId));
    }

    default Route check4RouteExistence(RouteRepository routeRepository, String routeId) throws Throwable {
        return routeRepository.findById(routeId).orElseThrow((Supplier<Throwable>) () -> new IdDoesNotExistsException("No route found with id: " + routeId));
    }

    default Mcc check4MccExistence(MccRepository mccRepository, String mccId) throws Throwable {
        return mccRepository.findById(mccId).orElseThrow((Supplier<Throwable>) () -> new IdDoesNotExistsException("No mcc found with id: " + mccId));
    }

    default Csp check4CspExistence(CspRepository cspRepository, String cspId) throws Throwable {
        return cspRepository.findById(cspId).orElseThrow((Supplier<Throwable>) () -> new IdDoesNotExistsException("No csp found with id: " + cspId));
    }

    default Farmer check4FarmerExistence(FarmerRepository farmerRepository, String farmerId) throws Throwable {
        return farmerRepository.findById(farmerId).orElseThrow((Supplier<Throwable>) () -> new IdDoesNotExistsException("No farmer found with id: " + farmerId));
    }

    /*default FieldExecutive check4FieldExecutiveExistence(FieldEx farmerRepository, String farmerId) throws Throwable {
        return farmerRepository.findById(farmerId).orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No farmer found with id: " + farmerId));
    }*/
}
