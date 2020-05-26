package com.thomas.apps.nhatrosvkltn.utils

import com.thomas.apps.nhatrosvkltn.model.Apartment
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.Image
import com.thomas.apps.nhatrosvkltn.model.User

class Data {
    companion object {
        val image1 = Image(
            1,
            "https://tin.nhadat.net/assets/uploads/2018/07/cho-thue-phong-tro-le-trong-tan-2.jpg"
        )
        val image2 = Image(
            2,
            "https://xaynhachothue.vn/wp-content/uploads/2019/10/cho-thue-day-nha-tro-1.jpg"
        )
        val image3 = Image(
            3,
            "https://news.mogi.vn/wp-content/uploads/2019/05/kinh-doanh-phong-tro-cho-thue-anh-1.jpg"
        )
        val image4 =
            Image(4, "https://i.thoibaokinhdoanh.vn/2020/03/10/phong-tro-7041-1583833666.jpg")
        val image5 = Image(
            5,
            "https://www.tapchithethao.com/wp-content/uploads/2019/09/top-4-kieu-nha-tro-dang-tro-thanh-trend-doi-voi-nguoi-thue-1.jpg"
        )
        val image6 = Image(6, "https://timviec365.com/pictures/news/2020/03/13/pvk1584102929.jpg")
        val image7 = Image(
            7,
            "https://cdn.thongtinduan.com/uploads/posts/2019-07/1562040097_nhung-kien-thuc-co-ban-can-biet-truoc-khi-ki-hop-dong-thue-nha-tro2.jpg"
        )
        val image8 = Image(
            8,
            "https://static123.com/phongtro123/uploads/images/thumbs/900x600/fit/2019/08/27/1_1566888997.jpg"
        )
        val image9 = Image(
            9,
            "https://image.tinnhanhchungkhoan.vn/715x540/uploaded/ngoctuanz/2017_10_01/07_yjkp.jpg"
        )
        val image10 = Image(
            10,
            "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BB12dna9.img?h=0&w=720&m=6&q=60&u=t&o=f&l=f&x=1566&y=1382"
        )
        val images1 = listOf(image1, image2, image3, image4, image5)
        val images2 = listOf(image2, image3, image4, image5, image6)
        val images3 = listOf(image3, image4, image5, image6, image7)
        val images4 = listOf(image4, image5, image6, image7, image8)
        val images5 = listOf(image5, image6, image7, image8, image9)
        val images6 = listOf(image6, image6, image7, image8, image9)
        val images7 = listOf(image7, image6, image7, image8, image9)
        val images8 = listOf(image8, image6, image7, image8, image9)
        val images9 = listOf(image9, image6, image7, image8, image9)
        val images10 = listOf(image10, image6, image7, image8, image9)

        val user1 = User(1, "Nam", "https://robohash.org/etfugabeatae.jpg?size=50x50&set=set1")
        val user2 =
            User(2, "Ánh", "https://robohash.org/excepturiquieligendi.jpg?size=50x50&set=set1")
        val user3 =
            User(3, "Lâm", "https://robohash.org/nostrumquisquamautem.bmp?size=50x50&set=set1")
        val user4 =
            User(4, "Hiệu", "https://robohash.org/culpaitaquevoluptatem.bmp?size=50x50&set=set1")
        val user5 =
            User(5, "Thiên", "https://robohash.org/ipsammodilaudantium.bmp?size=50x50&set=set1")

        val a1 = Apartment(
            1,
            "Phòng 40m ngay ĐH Mở mới xây đầy đủ bếp toilet riêng",
            "Cộng Hoà",
            "Quận 7",
            1F,
            10.7223275,
            106.7097714,
            "mo ta 1",
            "Hà",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = true,
            time = false,
            key = true,
            parking = false,
            air = true,
            heater = false,
            images = images1,
            user = user1
        )
        val a2 = Apartment(
            2,
            "PHÒNG MỚI GIÁ CHỈ TỪ 2 triệu(GẦN CHỢ THẠCH ĐÀ)",
            "Phường 7",
            "Gò vấp",
            2F,
            10.8199886,
            106.658863,
            "mo ta 2",
            "Thịnh",
            "0345653577",
            "1/1/2020",
            3000000,
            5000,
            15000,
            20,
            wifi = false,
            time = true,
            key = true,
            parking = true,
            air = false,
            heater = true,
            images = images2,
            user = user2
        )
        val a3 = Apartment(
            3,
            "Phòng full NT, free giặt",
            "gần cầu Tân Thuận.",
            "Quận 7",
            3F,
            10.7376658,
            106.6933228,
            "mo ta 3",
            "Phước",
            "0345653577",
            "1/2/2020",
            2000000,
            7000,
            15000,
            20,
            wifi = true,
            time = true,
            key = true,
            parking = false,
            air = true,
            heater = false,
            images = images3,
            user = user3
        )
        val a4 = Apartment(
            4,
            "Phòng mới các Công Viên PM Quang Trung 50m",
            "Phan huy ích",
            "Gò vấp",
            4F,
            10.8199886,
            106.658863,
            "mo ta 4",
            "Phúc",
            "0121484321",
            "5/5/2020",
            3000000,
            6000,
            15000,
            40,
            wifi = true,
            time = false,
            key = false,
            parking = false,
            air = true,
            heater = true,
            images = images4,
            user = user4
        )
        val a5 = Apartment(
            5,
            "@1134.Homestay Hoang Phuc cao cấp",
            "Hai bà trưng",
            "Quận 7",
            5F,
            10.7376658,
            106.6933228,
            "mo ta 5",
            "Kiệt",
            "0345653577",
            "7/6/2020",
            3000000,
            2000,
            12000,
            60,
            wifi = true,
            time = true,
            key = true,
            parking = true,
            air = false,
            heater = false,
            images = images5,
            user = user5
        )
        val a6 = Apartment(
            6,
            "KHÁCH SẠN KÝ TÚC XÁ NGAY VÒNG XOAY LÝ THÁI TỔ",
            "Lý thái tổ",
            "Quận 3",
            2F,
            10.779269,
            106.6723693,
            "mo ta 5",
            "Tuấn",
            "0125783448",
            "8/9/2020",
            2000000,
            3000,
            11000,
            15,
            wifi = false,
            time = false,
            key = false,
            parking = false,
            air = true,
            heater = true,
            images = images6,
            user = user1
        )
        val a7 = Apartment(
            7,
            "Cho thuê phòng trọ đầy đủ nội thất cao cấp",
            "Hoàng Văn Thụ",
            "Quận 10",
            4F,
            10.7727389,
            106.6606732,
            "mo ta 5",
            "Sơn",
            "0254813978",
            "23/1/2020",
            3000000,
            5000,
            10000,
            25,
            wifi = true,
            time = true,
            key = true,
            parking = false,
            air = false,
            heater = false,
            images = images7,
            user = user2
        )
        val a8 = Apartment(
            8,
            "Cho thuê phòng, ở ghép phòng nam,nữ riêng",
            "Phan Văn trị",
            "Gò Vấp",
            3F,
            10.8199886,
            106.658863,
            "mo ta 5",
            "Tùng",
            "0016487887",
            "30/9/2020",
            4000000,
            1000,
            18000,
            45,
            wifi = true,
            time = false,
            key = false,
            parking = true,
            air = true,
            heater = true,
            images = images8,
            user = user3
        )
        val a9 = Apartment(
            9,
            "Phòng trọ có gác cao",
            "Lê Thánh Tôn",
            "Tân phú",
            3.5F,
            10.7915398,
            106.5923849,
            "mo ta 5",
            "Anh",
            "0327478415",
            "1/4/2020",
            6000000,
            3000,
            11000,
            35,
            wifi = false,
            time = true,
            key = true,
            parking = false,
            air = true,
            heater = false,
            images = images9,
            user = user4
        )
        val a10 = Apartment(
            10,
            "Phòng trọ mới xây",
            "Lạc long quân",
            "Tân Bình",
            5F,
            10.8036298,
            106.6182534,
            "mo ta 5",
            "Thanh",
            "0158445455",
            "4/7/2020",
            8000000,
            2000,
            18000,
            20,
            wifi = false,
            time = false,
            key = true,
            parking = true,
            air = false,
            heater = false,
            images = images10,
            user = user5
        )

        val comment11 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment12 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment13 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment14 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment15 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment16 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment17 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment18 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment21 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment22 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment23 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment24 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment25 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment26 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment27 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment28 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment31 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment32 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment33 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment34 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment35 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment36 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment37 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment38 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment41 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment42 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment43 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment44 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment45 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment46 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment47 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment48 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment51 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment52 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment53 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment54 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment55 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment56 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment57 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment58 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment61 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment62 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment63 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment64 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment65 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment66 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment67 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment68 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment71 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment72 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment73 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment74 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment75 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment76 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment77 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment78 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment81 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment82 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment83 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment84 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment85 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment86 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment87 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment88 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment91 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment92 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment93 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment94 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment95 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment96 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment97 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment98 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment111 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment112 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment113 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment114 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment115 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment116 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment117 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment118 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)

        val comment1 = Comment(1, "Thời gian thuê tối đa là bao lâu", "2/1/2022", user1, a1)
        val comment2 = Comment(2, "Phòng có cho nấu ăn không", "1/4/2022", user2, a1)
        val comment3 = Comment(3, "Phòng ở được mấy người", "5/6/2022", user3, a1)
        val comment4 = Comment(4, "Có nuôi thú cưng được không vậy", "11/3/2022", user4, a1)
        val comment5 = Comment(5, "Phòng còn cho thuê không", "5/5/2022", user5, a1)
        val comment6 = Comment(6, "Chỗ ở an ninh không vậy", "2/1/2022", user1, a1)
        val comment7 = Comment(7, "để xe trong hay ngoài vậy", "1/4/2022", user2, a1)
        val comment8 = Comment(8, "Nhà trọ gần bến xe buýt không", "5/6/2022", user3, a1)


        val listComments1 =
            listOf(comment1, comment2, comment3, comment4, comment5, comment6, comment7, comment8)
        val listComments2 = listOf(
            comment111,
            comment112,
            comment113,
            comment114,
            comment115,
            comment116,
            comment117,
            comment118
        )
        val listComments3 = listOf(
            comment11,
            comment12,
            comment13,
            comment14,
            comment15,
            comment16,
            comment17,
            comment18
        )
        val listComments4 = listOf(
            comment21,
            comment22,
            comment23,
            comment24,
            comment25,
            comment26,
            comment27,
            comment28
        )
        val listComments5 = listOf(
            comment31,
            comment32,
            comment33,
            comment34,
            comment35,
            comment36,
            comment37,
            comment38
        )
        val listComments6 = listOf(
            comment41,
            comment42,
            comment43,
            comment44,
            comment45,
            comment46,
            comment47,
            comment48
        )
        val listComments7 = listOf(
            comment51,
            comment52,
            comment53,
            comment54,
            comment55,
            comment56,
            comment57,
            comment58
        )
        val listComments8 = listOf(
            comment61,
            comment62,
            comment63,
            comment64,
            comment65,
            comment66,
            comment67,
            comment68
        )
        val listComments9 = listOf(
            comment71,
            comment72,
            comment73,
            comment74,
            comment75,
            comment76,
            comment77,
            comment78
        )
        val listComments10 = listOf(
            comment81,
            comment82,
            comment83,
            comment84,
            comment85,
            comment86,
            comment87,
            comment88
        )


        val listApartments = listOf(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)
    }
}