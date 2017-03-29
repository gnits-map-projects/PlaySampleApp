package controllers;

import com.google.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ImageStore;
import services.LocalFileImageStore;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

public class ImageController extends Controller {

    private ImageStore imageStore;

    @Inject
    public ImageController(ImageStore imageStore) {
        this.imageStore = imageStore;
    }

    public Result uploadImage() {

        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        if (body == null) {
            return badRequest("Not multipart request");
        }

        Http.MultipartFormData.FilePart<File> image = body.getFile("image");
        if (image == null) {
            return badRequest("Missing image file in multi part request");
        }

        Logger.debug("Content type: {}", image.getContentType());
        if (!image.getContentType().equals("image/png")) {
            return badRequest("Only png images are supported");
        }

        try {
            final String imageId = imageStore.save(image.getFile());
            final URL downloadUrl = imageStore.downloadUrl(imageId, request());
            Logger.debug("Download url: {}", downloadUrl);

            return ok(Json.toJson(downloadUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError("Failed to store uploaded file");
        }

    }

    public Result downloadImage(String id) {
        final InputStream stream = imageStore.get(id);
        if (null == stream) {
            return notFound("Image not found");
        }
        return ok(stream);
    }

    public Result deleteImage(String id) {
        if (!imageStore.delete(id)) {
            notFound("Image not found");
        }
        return ok();
    }

}
