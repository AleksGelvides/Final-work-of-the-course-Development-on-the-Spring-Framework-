package com.finalProject.finalProjectDevOnSpring.testUtils;

import com.finalProject.finalProjectDevOnSpring.enumeration.RoleType;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationException;
import com.finalProject.finalProjectDevOnSpring.exception.ApplicationNotFoundException;
import com.finalProject.finalProjectDevOnSpring.models.entity.hotel.Room;
import com.finalProject.finalProjectDevOnSpring.models.entity.user.UserRole;
import com.finalProject.finalProjectDevOnSpring.models.repository.RoleRepository;
import com.finalProject.finalProjectDevOnSpring.models.repository.RoomRepository;
import com.finalProject.finalProjectDevOnSpring.mapper.hotel.HotelMapper;
import com.finalProject.finalProjectDevOnSpring.services.hotel.HotelService;
import com.finalProject.finalProjectDevOnSpring.services.user.UserService;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelChangeRequest;
import com.finalProject.finalProjectDevOnSpring.web.dto.hotel.HotelDto;
import com.finalProject.finalProjectDevOnSpring.web.dto.user.UserChangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class InitTestsUtils {

    public static HotelDto HOTEL = null;


    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    public void setupDb() throws Exception {
        initHotel();
        initRooms();
        initRoles();
        initUsers();
    }

    private void initHotel() throws ApplicationNotFoundException, ApplicationException {
        var hotel1 = new HotelChangeRequest(
                "Hotel 1",
                "Welcome to hotel 1",
                "Moscow",
                "Address 1",
                21312312L
        );
        HOTEL = hotelService.saveOrUpdate(null, hotel1);
    }

    private void initRooms() {
        List<Room> rooms = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            rooms.add(new Room()
                    .setRoomName(MessageFormat.format("Room #{0}", i))
                    .setRoomDescription(MessageFormat.format("Room #{0}", i))
                    .setRoomNumber(i)
                    .setMaximumCapacity(3)
                    .setRoomPrice(BigDecimal.ONE)
                    .setHotel(hotelMapper.toEntity(HOTEL))
            );
        }
        roomRepository.saveAll(rooms);
    }

    private void initRoles() {
        roleRepository.saveAll(
                List.of(
                        new UserRole().setRole(RoleType.ROLE_USER),
                        new UserRole().setRole(RoleType.ROLE_ADMIN
                        )
                ));
    }

    private void initUsers() throws Exception {
        userService.saveOrUpdate(null, new UserChangeRequest(
                "user", "user", "user",
                Set.of(RoleType.ROLE_USER)));
        userService.saveOrUpdate(null, new UserChangeRequest(
                "admin", "admin", "admin",
                Set.of(RoleType.ROLE_USER, RoleType.ROLE_ADMIN)));
    }
}
