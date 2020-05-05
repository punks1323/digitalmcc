package com.cluster.digital.service.impl;

import com.cluster.digital.database.entity.Mcc;
import com.cluster.digital.database.entity.Route;
import com.cluster.digital.exception.NotFoundException;
import com.cluster.digital.model.request.MccDTORequest;
import com.cluster.digital.model.response.MccDTOResponse;
import com.cluster.digital.repo.MccRepository;
import com.cluster.digital.repo.RouteRepository;
import com.cluster.digital.service.MccService;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-05
 */
public class MccServiceImpl implements MccService {
    private MccRepository mccRepository;
    private RouteRepository routeRepository;

    public MccServiceImpl(MccRepository mccRepository, RouteRepository routeRepository) {
        this.mccRepository = mccRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public MccDTOResponse createNewMcc(MccDTORequest mccDTORequest) throws Throwable {
        Optional<Route> route = routeRepository.findById(mccDTORequest.getRouteId());
        route.orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No route found with given id: " + mccDTORequest.getRouteId()));

        Mcc mcc = new Mcc();
        mcc.setName(mccDTORequest.getName());
        mcc.setAddress(mccDTORequest.getAddress());
        mcc.setPincode(mccDTORequest.getPincode());
        mcc.setVillage(mccDTORequest.getVillage());
        mcc.setTaluk(mccDTORequest.getTaluk());
        mcc.setLatitude(mccDTORequest.getLatitude());
        mcc.setLongitude(mccDTORequest.getLongitude());
        mcc.setRoute(route.get());

        Mcc savedMcc = mccRepository.save(mcc);
        if (!mccDTORequest.getFile().isEmpty()) {
            MultipartFile file = mccDTORequest.getFile();
            String fileName = "/tmp/digitalmcc/" + savedMcc.getId() + "_" + file.getOriginalFilename();
            Path path = Paths.get(fileName);
            Files.write(path, file.getBytes());
            mcc.setImage(fileName);
            savedMcc = mccRepository.save(mcc);
        }
        return savedMcc.getResponseDTO();
    }
}
