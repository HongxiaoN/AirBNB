-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 05, 2022 at 10:04 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `C43Project`
--

-- --------------------------------------------------------

--
-- Table structure for table `changeprice`
--

CREATE TABLE `changeprice` (
  `lid` int(5) NOT NULL,
  `start_time` int(8) NOT NULL,
  `end_time` int(8) NOT NULL,
  `price` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `lists`
--

CREATE TABLE `lists` (
  `lid` int(5) NOT NULL,
  `house_type` varchar(15) NOT NULL,
  `latitude` int(4) NOT NULL,
  `longitude` int(4) NOT NULL,
  `address` varchar(15) NOT NULL,
  `postal_code` int(4) NOT NULL,
  `city` varchar(20) NOT NULL,
  `country` varchar(20) NOT NULL,
  `bathroom_hair_dryer` tinyint(1) NOT NULL DEFAULT 0,
  `bathroom_cleaning_products` tinyint(1) NOT NULL DEFAULT 0,
  `bedroom_essentials` tinyint(1) NOT NULL DEFAULT 0,
  `bedroom_hangers` tinyint(1) NOT NULL DEFAULT 0,
  `kitchen_dishes` tinyint(1) NOT NULL DEFAULT 0,
  `kitchen_fridge` tinyint(1) NOT NULL DEFAULT 0,
  `default_price` int(4) NOT NULL,
  `created_at` int(8) NOT NULL,
  `exist` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `owns`
--

CREATE TABLE `owns` (
  `uid` int(9) NOT NULL,
  `lid` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `hostid` int(9) NOT NULL,
  `renterid` int(9) NOT NULL,
  `lid` int(5) NOT NULL,
  `start_date` int(8) NOT NULL,
  `end_date` int(8) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 0,
  `host_rate` int(1) NOT NULL DEFAULT 3,
  `host_comment` varchar(1000) DEFAULT NULL,
  `renter_rate` int(1) NOT NULL DEFAULT 3,
  `renter_comment` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` int(20) NOT NULL,
  `name` varchar(40) NOT NULL,
  `age` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `name`, `age`) VALUES
(1, 'Prince', 4),
(2, 'asd', 4),
(3, 'Niu', 11),
(4, 'Maggie', 5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
 `sin` int(9) NOT NULL,
 `username` varchar(10) NOT NULL,
 `email` varchar(20) NOT NULL,
 `address` varchar(40) NOT NULL,
 `birthday` int(8) NOT NULL,
 `occupation` varchar(20) NOT NULL,
 `card` int(16) NOT NULL,
 PRIMARY KEY (`sin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

--
-- Indexes for dumped tables
--

--
-- Indexes for table `changeprice`
--
ALTER TABLE `changeprice`
  ADD PRIMARY KEY (`lid`,`start_time`,`end_time`);

--
-- Indexes for table `lists`
--
ALTER TABLE `lists`
  ADD PRIMARY KEY (`lid`),
  ADD UNIQUE KEY `address` (`address`);

--
-- Indexes for table `owns`
--
ALTER TABLE `owns`
  ADD PRIMARY KEY (`uid`,`lid`),
  ADD KEY `lid` (`lid`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`hostid`,`renterid`,`lid`),
  ADD KEY `temp2` (`lid`),
  ADD KEY `renterid` (`renterid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`sin`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `changeprice`
--
ALTER TABLE `changeprice`
  ADD CONSTRAINT `changeprice_ibfk_1` FOREIGN KEY (`lid`) REFERENCES `lists` (`lid`);

--
-- Constraints for table `owns`
--
ALTER TABLE `owns`
  ADD CONSTRAINT `owns_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `users` (`sin`),
  ADD CONSTRAINT `owns_ibfk_2` FOREIGN KEY (`lid`) REFERENCES `lists` (`lid`);

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`hostid`) REFERENCES `users` (`sin`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`renterid`) REFERENCES `users` (`sin`),
  ADD CONSTRAINT `temp2` FOREIGN KEY (`lid`) REFERENCES `lists` (`lid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
