-- phpMyAdmin SQL Dump
-- version 3.4.8
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Sam 14 Janvier 2012 à 19:23
-- Version du serveur: 5.5.13
-- Version de PHP: 5.3.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `gestionUniversite`
--

-- --------------------------------------------------------

--
-- Structure de la table `Formation`
--

CREATE TABLE IF NOT EXISTS `Formation` (
  `nom` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  `nomUniversite` varchar(200) NOT NULL,
  `nomSalleCM` varchar(200) NOT NULL,
  `nomSalleTD` varchar(200) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `ForeignUniv` (`nomUniversite`),
  KEY `ForeignTD` (`nomSalleTD`),
  KEY `ForeignCM` (`nomSalleCM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Formation`
--

INSERT INTO `Formation` (`nom`, `code`, `nomUniversite`, `nomSalleCM`, `nomSalleTD`) VALUES
('Licence 3 Informatique', 'L3I', 'UHP', 'E302', 'E303'),
('Master 1 Informatique', 'M1INFO', 'UHP', 'E300', 'E301'),
('Master 2 SSR', 'M2SSR', 'UHP', 'E300', 'E300');

-- --------------------------------------------------------

--
-- Structure de la table `Module`
--

CREATE TABLE IF NOT EXISTS `Module` (
  `code` varchar(200) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `coeffTP` int(11) NOT NULL,
  `coeffTD` int(11) NOT NULL,
  `coeffCM` int(11) NOT NULL,
  `coeffModule` int(11) NOT NULL,
  `loginResponsable` varchar(8) NOT NULL,
  `codeFormation` varchar(200) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `ForeignResponsable` (`loginResponsable`),
  KEY `codeFormation` (`codeFormation`),
  KEY `codeFormation_2` (`codeFormation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Module`
--

INSERT INTO `Module` (`code`, `nom`, `coeffTP`, `coeffTD`, `coeffCM`, `coeffModule`, `loginResponsable`, `codeFormation`) VALUES
('LMC', 'Logique Modele Calcul', 0, 0, 0, 0, 'cirs0001', 'M1INFO');

-- --------------------------------------------------------

--
-- Structure de la table `Participe`
--

CREATE TABLE IF NOT EXISTS `Participe` (
  `login` varchar(8) NOT NULL,
  `codeModule` varchar(200) NOT NULL,
  `noteCM` float DEFAULT NULL,
  `noteTD` float DEFAULT NULL,
  `noteTP` float DEFAULT NULL,
  PRIMARY KEY (`login`,`codeModule`),
  KEY `participe_ibfk_2` (`codeModule`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Salle`
--

CREATE TABLE IF NOT EXISTS `Salle` (
  `nom` varchar(200) NOT NULL,
  `capacite` int(10) NOT NULL,
  PRIMARY KEY (`nom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Salle`
--

INSERT INTO `Salle` (`nom`, `capacite`) VALUES
('E300', 20),
('E301', 30),
('E302', 30),
('E303', 40);

-- --------------------------------------------------------

--
-- Structure de la table `Seance`
--

CREATE TABLE IF NOT EXISTS `Seance` (
  `type` varchar(2) NOT NULL,
  `jour` varchar(10) NOT NULL,
  `heure` int(2) NOT NULL,
  `nomSalle` varchar(200) NOT NULL,
  `codeModule` varchar(200) NOT NULL,
  PRIMARY KEY (`type`,`jour`,`heure`),
  KEY `ForeignSalle` (`nomSalle`),
  KEY `ForeignModule` (`codeModule`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `Universite`
--

CREATE TABLE IF NOT EXISTS `Universite` (
  `nom` varchar(200) NOT NULL,
  PRIMARY KEY (`nom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Universite`
--

INSERT INTO `Universite` (`nom`) VALUES
('UHP');

-- --------------------------------------------------------

--
-- Structure de la table `Utilisateur`
--

CREATE TABLE IF NOT EXISTS `Utilisateur` (
  `login` varchar(8) NOT NULL,
  `mdp` varchar(200) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `prenom` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `nomUniversite` varchar(200) NOT NULL,
  `codeFormation` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`login`),
  KEY `ForeignUniv` (`nomUniversite`),
  KEY `foreignFormation` (`codeFormation`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `Utilisateur`
--

INSERT INTO `Utilisateur` (`login`, `mdp`, `nom`, `prenom`, `type`, `nomUniversite`, `codeFormation`) VALUES
('cirs0001', 'aaa', 'CIRSTEA', 'Horatiu', 'Professeur', 'UHP', NULL),
('coch0081', 'aaa', 'COCHARD', 'Swann', 'Etudiant', 'UHP', 'M1INFO'),
('galm0001', 'aaa', 'GALMICHE', 'Didier', 'Professeur', 'UHP', NULL),
('guil1731', 'aaa', 'GUILLAUME', 'Maxime', 'Etudiant', 'UHP', 'M1INFO'),
('varl0078', 'aaa', 'VARLET', 'Gael', 'Etudiant', 'UHP', 'M1INFO');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Formation`
--
ALTER TABLE `Formation`
  ADD CONSTRAINT `formation_ibfk_1` FOREIGN KEY (`nomUniversite`) REFERENCES `Universite` (`nom`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `formation_ibfk_2` FOREIGN KEY (`nomSalleCM`) REFERENCES `Salle` (`nom`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `formation_ibfk_3` FOREIGN KEY (`nomSalleTD`) REFERENCES `Salle` (`nom`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `Module`
--
ALTER TABLE `Module`
  ADD CONSTRAINT `module_ibfk_2` FOREIGN KEY (`codeFormation`) REFERENCES `Formation` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `module_ibfk_1` FOREIGN KEY (`loginResponsable`) REFERENCES `Utilisateur` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `Participe`
--
ALTER TABLE `Participe`
  ADD CONSTRAINT `participe_ibfk_1` FOREIGN KEY (`login`) REFERENCES `Utilisateur` (`login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `participe_ibfk_2` FOREIGN KEY (`codeModule`) REFERENCES `Module` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `Seance`
--
ALTER TABLE `Seance`
  ADD CONSTRAINT `seance_ibfk_1` FOREIGN KEY (`nomSalle`) REFERENCES `Salle` (`nom`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `seance_ibfk_2` FOREIGN KEY (`codeModule`) REFERENCES `Module` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `Utilisateur`
--
ALTER TABLE `Utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`nomUniversite`) REFERENCES `Universite` (`nom`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `utilisateur_ibfk_2` FOREIGN KEY (`codeFormation`) REFERENCES `Formation` (`code`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
