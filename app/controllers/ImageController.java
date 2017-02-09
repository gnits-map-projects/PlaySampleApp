package controllers;

import com.google.inject.Inject;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ImageStore;

import java.io.File;
import java.io.IOException;
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

        final Path source = image.getFile().toPath();
        try {
            final String imageId = imageStore.storeImage(source);
            final String downloadUrl = routes.ImageController.downloadImage(imageId).absoluteURL(request());
            Logger.debug("Download url: {}", downloadUrl);

            return ok(Json.toJson(downloadUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return internalServerError("Failed to store uploaded file");
        }

    }

    public Result downloadImage(String id) {
        final File file = imageStore.getImage(id);
        if (null == file) {
            return notFound("Image not found");
        }
        return ok(file);
    }

    public Result deleteImage(String id) {
        try {
            if (!imageStore.deleteImage(id)) {
                notFound("Image not found");
            }
            return ok();
        } catch (IOException e) {
            e.printStackTrace();
            return internalServerError("Failed to delete image file");
        }
    }

}
