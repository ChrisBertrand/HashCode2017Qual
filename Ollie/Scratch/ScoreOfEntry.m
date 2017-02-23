function [ score ] = ScoreOfEntry( lD, lC, nR, vS )
%SCOREOFENTRY Summary of this function goes here
%   Detailed explanation goes here

score = (lD - lC) * nR / vS;

end

