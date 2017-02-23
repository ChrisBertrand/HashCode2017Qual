function [ Data ] = ReadFile( Filename )
%READFILE Summary of this function goes here
%   Detailed explanation goes here

h = fopen(Filename);

in = cell2mat(textscan(fgetl(h),'%d %d %d %d %d'));

Data.nV = in(1);
Data.nE = in(2);
Data.nR = in(3);
Data.nC = in(4);
Data.sC = in(5);

Data.V = cell2mat(textscan(fgetl(h),'%d'));

for iEndPoint = 1:Data.nE

    tline = fgetl(h);
    
    in = textscan(tline,'%d %d');
    
    Endpoint.dL= in{1};
    Endpoint.nC = in{2};
    
    Endpoint.Caches = zeros(2,Endpoint.nC);
    
    for iCache = 1:Endpoint.nC
        tline = fgetl(h);
        Cache = cell2mat(textscan(tline, '%d %d'))';
        Endpoint.Caches(:,iCache) = Cache;
    end
    
    Data.E(iEndPoint) = Endpoint;
end

Data.R = zeros(3,Data.nR);

for iRequest = 1:Data.nR
    in = cell2mat(textscan(fgetl(h),'%d %d %d'))';
    Data.R(:,iRequest) = in;
end

end

