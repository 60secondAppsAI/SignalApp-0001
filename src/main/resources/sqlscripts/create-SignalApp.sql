CREATE DATABASE IF NOT EXISTS SignalApp;

CREATE TABLE SignalApp.signals (
    `signal_id` INT AUTO_INCREMENT,
    `strategy_id` INT,
    `instrument_id` INT,
    `signal_type` VARCHAR(255),
    `strength` DOUBLE,
    `generated_at` DATETIME,
PRIMARY KEY (signal_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE SignalApp.signal_inputs (
    `signal_input_id` INT AUTO_INCREMENT,
    `signal_id` INT,
    `input_name` VARCHAR(255),
    `input_value` DOUBLE,
PRIMARY KEY (signal_input_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE SignalApp.signal_rules (
    `signal_rule_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `description` VARCHAR(255),
    `rule_type` VARCHAR(255),
    `active` BOOLEAN,
PRIMARY KEY (signal_rule_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE SignalApp.signal_events (
    `signal_event_id` INT AUTO_INCREMENT,
    `signal_id` INT,
    `event_type` VARCHAR(255),
    `occurred_at` DATETIME,
PRIMARY KEY (signal_event_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
