
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "-03:00";

CREATE DATABASE IF NOT EXISTS `dbamain` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `dbamain`;

CREATE TABLE `tarefa` (
  `id` int NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `descricao` varchar(1000) DEFAULT NULL,
  `status` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'P',
  `data_criacao` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_conclusao` datetime DEFAULT NULL,
  `responsavel` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `tarefa`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `tarefa`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;
COMMIT;