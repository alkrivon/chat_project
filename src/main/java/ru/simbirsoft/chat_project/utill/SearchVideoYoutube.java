package ru.simbirsoft.chat_project.utill;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchVideoYoutube {

    private String apikey;
    private YouTube youTube;

    public SearchVideoYoutube(String apikey) {
        if (youTube == null) {
            youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {
                }
            }).setApplicationName("chat-bot-youtube").build();
            this.apikey = apikey;
        }
    }

    public BigInteger getViewCount(String videoID) throws IOException{

        YouTube.Videos.List listVideosRequest = youTube.videos().list("statistics");
        listVideosRequest.setId(videoID);
        listVideosRequest.setKey(apikey);
        VideoListResponse listResponse = listVideosRequest.execute();
        Video video = listResponse.getItems().get(0);
        return video.getStatistics().getViewCount();
    }

    public BigInteger getLikeCount(String videoID) throws IOException{

        YouTube.Videos.List listVideosRequest = youTube.videos().list("statistics");
        listVideosRequest.setId(videoID);
        listVideosRequest.setKey(apikey);
        VideoListResponse listResponse = listVideosRequest.execute();
        Video video = listResponse.getItems().get(0);
        return video.getStatistics().getLikeCount();
    }

    public String getChannelName(String channelID) throws IOException {

        YouTube.Channels.List listChannelsRequest = youTube.channels().list("snippet");
        listChannelsRequest.setId(channelID);
        listChannelsRequest.setKey(apikey);
        ChannelListResponse listResponse = listChannelsRequest.execute();
        Channel channel = listResponse.getItems().get(0);
        return channel.getSnippet().getTitle();
    }

    public List<String> getChannelVideos(String channelID) throws IOException {

        YouTube.Channels.List listChannelsRequest = youTube.channels().list("contentDetails");
        listChannelsRequest.setId(channelID);
        listChannelsRequest.setKey(apikey);
        ChannelListResponse listResponse = listChannelsRequest.execute();
        Channel channel = listResponse.getItems().get(0);

        List<String> videoIdList = new ArrayList<>();
        YouTube.PlaylistItems.List playlistItemRequest = youTube.playlistItems().list("contentDetails");
        playlistItemRequest.setKey(apikey);
        playlistItemRequest.setFields("nextPageToken,items/contentDetails/videoId");
        playlistItemRequest.setMaxResults(5L);
        playlistItemRequest.setPlaylistId(channel.getContentDetails().getRelatedPlaylists().getUploads());

        String nextToken = "";
        do {
            playlistItemRequest.setPageToken(nextToken);
            PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

            for (PlaylistItem playlistItem: playlistItemResult.getItems())
                videoIdList.add(playlistItem.getContentDetails().getVideoId());

            nextToken = playlistItemResult.getNextPageToken();
        } while (nextToken != null);
        List<Video> videoList = new ArrayList<>();
        while (videoIdList.size() > 0) {
            int endIndex = Math.min(videoIdList.size(), 5);
            List<String> subList = videoIdList.subList(0, endIndex);

            YouTube.Videos.List videosRequest =
                    youTube.videos().list("id");
            videosRequest.setKey(apikey);
            videosRequest.setId(String.join(",", subList));
            VideoListResponse videosResponse = videosRequest.execute();
            videoList.addAll(videosResponse.getItems());
            subList.clear();
        }
        List<String> URLVideo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            URLVideo.add("https://www.youtube.com/watch?v=" + videoList.get(i).getId());
        }
        return URLVideo;
    }

   public String getComments(String videoId) throws IOException {

        YouTube.CommentThreads.List listCommetnRequest = youTube.commentThreads().list("id, snippet");
        listCommetnRequest.setVideoId(videoId);
        listCommetnRequest.setMaxResults(100L);
        listCommetnRequest.setKey(apikey);

        List<CommentThread> comments = new ArrayList<>();
        String pageToken = "";

        do {
            CommentThreadListResponse response = listCommetnRequest.setPageToken(pageToken).execute();
            comments.addAll(response.getItems());
            pageToken = response.getNextPageToken();
        }
        while (pageToken != null);
        Random random = new Random();
        Integer numberOfComment = random.ints(0, 99)
               .findFirst()
               .getAsInt();
        return "Login: " + comments.get(numberOfComment).getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName() + "\n" +
                "Comment: " + comments.get(numberOfComment).getSnippet().getTopLevelComment().getSnippet().getTextOriginal();
    }
}
