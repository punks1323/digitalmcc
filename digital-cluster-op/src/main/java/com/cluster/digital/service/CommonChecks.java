package com.cluster.digital.service;

import com.cluster.digital.database.entity.*;
import com.cluster.digital.database.repo.*;
import com.cluster.digital.exception.IdsNotFoundInDatabaseException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-11
 */
public interface CommonChecks {
    default Collection<Cluster> verifyAllClusterIds(ClusterRepository clusterRepository, Collection<String> clusterIds) throws IdsNotFoundInDatabaseException {
        List<Cluster> allClusters = clusterRepository.findAllById(clusterIds);
        if (allClusters.size() == clusterIds.size())
            return allClusters;

        List<String> clusterIdsInDB = allClusters.stream().map(Cluster::getId).collect(Collectors.toList());
        clusterIds.removeAll(clusterIdsInDB);
        throw new IdsNotFoundInDatabaseException("Cluster does not exists for id: " + clusterIds);
    }

    default Collection<Dairy> verifyAllDairyIds(DairyRepository dairyRepository, Collection<String> dairyIds) throws IdsNotFoundInDatabaseException {
        List<Dairy> allDairies = dairyRepository.findAllById(dairyIds);
        if (allDairies.size() == dairyIds.size())
            return allDairies;

        List<String> dairyIdsInDB = allDairies.stream().map(Dairy::getId).collect(Collectors.toList());
        dairyIds.removeAll(dairyIdsInDB);
        throw new IdsNotFoundInDatabaseException("Dairy does not exists for id: " + dairyIds);
    }

    default Collection<Route> verifyAllRouteIds(RouteRepository routeRepository, Collection<String> routeIds) throws IdsNotFoundInDatabaseException {
        List<Route> allRoutes = routeRepository.findAllById(routeIds);
        if (allRoutes.size() == routeIds.size())
            return allRoutes;

        List<String> routeIdsInDB = allRoutes.stream().map(Route::getId).collect(Collectors.toList());
        routeIds.removeAll(routeIdsInDB);
        throw new IdsNotFoundInDatabaseException("Route does not exists for id: " + routeIds);
    }

    default Collection<Mcc> verifyAllMccIds(MccRepository mccRepository, Collection<String> mccIds) throws IdsNotFoundInDatabaseException {
        List<Mcc> allMcc = mccRepository.findAllById(mccIds);
        if (allMcc.size() == mccIds.size())
            return allMcc;

        List<String> mccIdsInDB = allMcc.stream().map(Mcc::getId).collect(Collectors.toList());
        mccIds.removeAll(mccIdsInDB);
        throw new IdsNotFoundInDatabaseException("Mcc does not exists for id: " + mccIds);
    }

    default Collection<Csp> verifyAllCspIds(CspRepository cspRepository, Collection<String> cspIds) throws IdsNotFoundInDatabaseException {
        List<Csp> allCsp = cspRepository.findAllById(cspIds);
        if (allCsp.size() == cspIds.size())
            return allCsp;

        List<String> cspIdsInDB = allCsp.stream().map(Csp::getId).collect(Collectors.toList());
        cspIds.removeAll(cspIdsInDB);
        throw new IdsNotFoundInDatabaseException("Csp does not exists for id: " + cspIds);
    }

    default Collection<FieldExecutive> verifyAllFieldExecutiveIds(FieldExecutiveRepository fieldExecutiveRepository, Collection<String> fieldExecutiveIds) throws IdsNotFoundInDatabaseException {
        List<FieldExecutive> allFE = fieldExecutiveRepository.findAllById(fieldExecutiveIds);
        if (allFE.size() == fieldExecutiveIds.size())
            return allFE;

        List<String> feIdsInDB = allFE.stream().map(FieldExecutive::getId).collect(Collectors.toList());
        fieldExecutiveIds.removeAll(feIdsInDB);
        throw new IdsNotFoundInDatabaseException("Field Executive does not exists for id: " + fieldExecutiveIds);
    }

    default Collection<Farmer> verifyAllMccIds(FarmerRepository farmerRepository, Collection<String> farmerIds) throws IdsNotFoundInDatabaseException {
        List<Farmer> allFarmers = farmerRepository.findAllById(farmerIds);
        if (allFarmers.size() == farmerIds.size())
            return allFarmers;

        List<String> farmerIdsInDB = allFarmers.stream().map(Farmer::getId).collect(Collectors.toList());
        farmerIds.removeAll(farmerIdsInDB);
        throw new IdsNotFoundInDatabaseException("Farmer does not exist for id: " + farmerIds);
    }
}
