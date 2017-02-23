fclose all;

% Read in data
fName = 'videos_worth_spreading.in';
Data = ReadFile(fName);
save([fName '.hash'],'Data');

Results = zeros(Data.nC, Data.nV);

for iCache = 1:Data.nC
    for iVideo = 1:Data.nV
        % Get all requests that match this video and that can access this
        % cache
        RequestIdxWithVideo = find(Data.R(1,:) == iVideo-1);    
        
        for iRequest = RequestIdxWithVideo
            thisEndpoint = Data.R(2,iRequest);
            % Work out the score for each request (varies as endpoints may
            % vary)
            
            lD = Data.E(thisEndpoint+1).dL;
            %lC = % find this cache in endpoint entry and get latency
            cacheIdx = find(Data.E(thisEndpoint+1).Caches(1,:) == iCache-1);
            
            % Does this endpoint talk to this cahe at all?
            if ~isempty(cacheIdx)
                lC = Data.E(thisEndpoint+1).Caches(2,cacheIdx);
                nR = Data.R(3,iRequest);
                sV = Data.V(iVideo);

                Score = ScoreOfEntry(lD, lC, nR, sV);

                % Sum together
                Results(iCache, iVideo) = Results(iCache, iVideo) + Score;
            end 
        end
    end
end

VideosForCaches = cell(1,Data.nC);

for iCache = 1:Data.nC
    [~,idx] = sort(Results(iCache,:),'descend');
    nextIdx = 0;
    while((sum(Data.V(VideosForCaches{iCache}))+Data.V(idx(nextIdx+1))) < Data.sC)
        nextIdx = nextIdx + 1;
        VideosForCaches{iCache} = [VideosForCaches{iCache} idx(nextIdx)];
    end
end

outfile = [fName '.out'];
hout = fopen(outfile,'w');
fprintf(hout, '%d\n', Data.nC);
for iCache = 1:Data.nC
    fprintf(hout, '%d ', iCache-1);
    for iVideo = 1:numel(VideosForCaches{iCache})
        fprintf(hout, '%d ', VideosForCaches{iCache}(iVideo)-1);
    end
    fprintf(hout, '\n');
end