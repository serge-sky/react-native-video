import AVFoundation
import Promises
import AVKit
import Foundation

// MARK: - RCTVideoAssetsUtils

enum RCTVideoAssetsUtils {
    static func getMediaSelectionGroup(
        asset: AVAsset?,
        for mediaCharacteristic: AVMediaCharacteristic
    ) -> Promise<AVMediaSelectionGroup?> {
        if #available(iOS 15, tvOS 15, visionOS 1.0, *) {
            return wrap { handler in
                asset?.loadMediaSelectionGroup(for: mediaCharacteristic, completionHandler: handler)
            }
        } else {
            #if !os(visionOS)
                return Promise { fulfill, _ in
                    fulfill(asset?.mediaSelectionGroup(forMediaCharacteristic: mediaCharacteristic))
                }
            #endif
        }
    }

    static func getTracks(asset: AVAsset, withMediaType: AVMediaType) -> Promise<[AVAssetTrack]?> {
        if #available(iOS 15, tvOS 15, visionOS 1.0, *) {
            return wrap { handler in
                asset.loadTracks(withMediaType: withMediaType, completionHandler: handler)
            }
        } else {
            #if !os(visionOS)
                return Promise { fulfill, _ in
                    fulfill(asset.tracks(withMediaType: withMediaType))
                }
            #endif
        }
    }
}

/*!
 * Collection of helper functions for tvOS specific features
 */

#if os(tvOS)
    enum RCTVideoTVUtils {
        static func makeNavigationMarkerGroups(_ chapters: [Chapter]) -> [AVNavigationMarkersGroup] {
            var metadataGroups = [AVTimedMetadataGroup]()

            // Iterate over the defined chapters and build a timed metadata group object for each.
            for chapter in chapters {
                metadataGroups.append(makeTimedMetadataGroup(for: chapter))
            }

            return [AVNavigationMarkersGroup(title: nil, timedNavigationMarkers: metadataGroups)]
        }

        static func makeTimedMetadataGroup(for chapter: Chapter) -> AVTimedMetadataGroup {
            var metadata = [AVMetadataItem]()

            // Create a metadata item that contains the chapter title.
            let titleItem = RCTVideoUtils.createMetadataItem(for: .commonIdentifierTitle, value: chapter.title)
            metadata.append(titleItem)

            // Create a time range for the metadata group.
            let timescale: Int32 = 600
            let startTime = CMTime(seconds: chapter.startTime, preferredTimescale: timescale)
            let endTime = CMTime(seconds: chapter.endTime, preferredTimescale: timescale)
            let timeRange = CMTimeRangeFromTimeToTime(start: startTime, end: endTime)

            // Image
            if let imgUri = chapter.uri,
               let uri = URL(string: imgUri),
               let imgData = try? Data(contentsOf: uri),
               let image = UIImage(data: imgData),
               let pngData = image.pngData() {
                let imageItem = RCTVideoUtils.createMetadataItem(for: .commonIdentifierArtwork, value: pngData)
                metadata.append(imageItem)
            }

            return AVTimedMetadataGroup(items: metadata, timeRange: timeRange)
        }
    }
#endif
