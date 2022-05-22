-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 11, 2022 at 02:14 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `library`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `book_id` varchar(20) NOT NULL,
  `book_title` varchar(40) NOT NULL,
  `book_author` varchar(20) NOT NULL,
  `book_status` int(11) NOT NULL COMMENT '0:chưa cho mượn, 1: đã cho mượn',
  `book_code` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`book_id`, `book_title`, `book_author`, `book_status`, `book_code`) VALUES
('BOOK1', 'City Atlas', 'Federica Magrin', 0, 'BK1'),
('BOOK10', 'The Congratulations, You\'re Expecting!', 'Heidi Murkoff', 0, 'BK10'),
('BOOK11', 'Dạy con làm giàu', 'Robert Kiyosaki', 0, 'BK11'),
('BOOK2', 'Wonders of the World', 'Daniela Celli', 1, 'BK2'),
('BOOK3', 'Reader Digest Great Biographies', 'Daniela Celli', 1, 'BK3'),
('BOOK4', 'Volant Journals Pocket Ruled Forget', 'Moleskine', 1, 'BK4'),
('BOOK5', 'Fantastic Cities', 'Steve McDonald', 1, 'BK5'),
('BOOK6', 'Home by Ellen DeGeneres', 'Ellen DeGeneres', 1, 'BK6'),
('BOOK7', 'Creative Space Journal', 'Lucy Irving', 1, 'BK7'),
('BOOK8', 'Illustrated Treasury', 'Lucy Irving', 1, 'BK8'),
('BOOK9', 'Casino Royale Bond on Set', 'Various', 0, 'BK9');

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `borrow_id` varchar(20) NOT NULL,
  `borrow_begindate` varchar(30) NOT NULL,
  `borrow_enddate` varchar(30) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `borrow_status` int(11) NOT NULL COMMENT '0:Đăng ký mượn,1:Đã nhận sách,2: Đã trả sách,3:Đăng ký trả,4:Trả thiếu',
  `borrow_returndate` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`borrow_id`, `borrow_begindate`, `borrow_enddate`, `user_id`, `borrow_status`, `borrow_returndate`) VALUES
('BORROW1', '12-04-2022', '30-05-2022', 'USER1', 4, '11-05-2022'),
('BORROW2', '10-04-2022', '19-04-2022', 'USER2', 1, ''),
('BORROW3', '05-06-2022', '30-05-2022', 'USER3', 3, ''),
('BORROW4', '12-04-2022', '25-04-2022', 'USER4', 2, '24-04-2022');

-- --------------------------------------------------------

--
-- Table structure for table `borrow_detail`
--

CREATE TABLE `borrow_detail` (
  `book_id` varchar(20) NOT NULL,
  `borrow_id` varchar(20) NOT NULL,
  `borrowdetail_status` int(11) NOT NULL COMMENT '0:Chưa trả,1:Đã trả'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `borrow_detail`
--

INSERT INTO `borrow_detail` (`book_id`, `borrow_id`, `borrowdetail_status`) VALUES
('BOOK4', 'BORROW2', 0),
('BOOK6', 'BORROW3', 0),
('BOOK7', 'BORROW3', 0),
('BOOK8', 'BORROW4', 1),
('BOOK5', 'BORROW2', 0),
('BOOK1', 'BORROW1', 1),
('BOOK2', 'BORROW1', 0),
('BOOK3', 'BORROW1', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tag_read`
--

CREATE TABLE `tag_read` (
  `tag_rfid` varchar(100) NOT NULL,
  `book_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tag_read`
--

INSERT INTO `tag_read` (`tag_rfid`, `book_id`) VALUES
('E28011606000020958CD98FE', 'BOOK1'),
('E28011606000020958CE220E', 'BOOK2'),
('E28011606000020958CDF87E', 'BOOK3'),
('000000000000000000001314', 'BOOK4'),
('00B07A14285276D030000082', 'BOOK5'),
('E28011606000020958CD98EE', 'BOOK6'),
('E28011606000020958CDF86E', 'BOOK7'),
('E28011606000020958CD98FF', 'BOOK8');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(20) NOT NULL,
  `user_name` varchar(30) NOT NULL,
  `user_phone` varchar(20) NOT NULL,
  `user_loginname` varchar(30) NOT NULL,
  `user_password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_phone`, `user_loginname`, `user_password`) VALUES
('USER1', 'Nam Nam', '0999888777', 'namnam', 'namnam2301'),
('USER2', 'Bao Beo', '0111222333', 'baobao', 'baobao123'),
('USER3', 'Người dùng demo', '0999888778', 'userdemo', '123456'),
('USER4', 'Người mượn', '0999888799', 'usertest', '123456');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`book_id`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`borrow_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `borrow_detail`
--
ALTER TABLE `borrow_detail`
  ADD KEY `book_id` (`book_id`),
  ADD KEY `borrow_id` (`borrow_id`);

--
-- Indexes for table `tag_read`
--
ALTER TABLE `tag_read`
  ADD PRIMARY KEY (`tag_rfid`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `borrow_detail`
--
ALTER TABLE `borrow_detail`
  ADD CONSTRAINT `borrow_detail_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `borrow_detail_ibfk_2` FOREIGN KEY (`borrow_id`) REFERENCES `borrow` (`borrow_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tag_read`
--
ALTER TABLE `tag_read`
  ADD CONSTRAINT `tag_read_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
